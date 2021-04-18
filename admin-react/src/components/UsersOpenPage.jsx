import { Fragment, useEffect, useCallback } from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm, Controller } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import config from "%/config";
import axios from "axios";
import { diff } from "deep-object-diff";
import Textfield from "@atlaskit/textfield";
import Button, { ButtonGroup } from "@atlaskit/button";
import { useFlags } from "@atlaskit/flag";
import "react-mde/lib/styles/css/react-mde-all.css";
import { useHistory } from "react-router-dom";
import noNewDataToSubmitError from "%/errors/noNewDataToSubmit";
import successError from "%/errors/successError";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import toast from "%/utilities/toast";

export default () => {
    const history = useHistory();

    const { showFlag } = useFlags();

    const { username } = useParams();

    const defaultState = {
        schema: {},
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const requestItem = () =>
        axios({
            method: "GET",
            url: config.serverUrl + `v1/users/${username}/`
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: config.serverUrl + "v1/schemas/edit-users/"
        });

    const submitItem = data =>
        axios({
            method: "PATCH",
            url: config.serverUrl + `v1/users/${username}/`,
            data
        });

    const setSchema = schema =>
        setState(draft => {
            delete schema.properties.additional_notes.anyOf;
            schema.properties.additional_notes.type = "string";
            delete schema.properties.other_names.anyOf;
            schema.properties.other_names.type = "string";
            draft.schema = schema;
        });

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const schemaAction = () => {
        (async () => {
            try {
                const schema = await requestSchema();
                setSchema(schema.data);
            } catch (error) {}
        })();
    };

    const yupConfig = {
        abortEarly: false
    };

    const yupSchema = isObjectEmpty(state.schema)
        ? Yup.object()
        : buildYup(state.schema);

    const emptyStringsToNull = R.map(x => {
        if (typeof x === "string") {
            return x.trim().length === 0 ? null : x;
        } else {
            return x;
        }
    });

    const nullToEmptyString = x => (x === null ? "" : x);

    const formatYupErrors = R.pipe(
        R.map(e => ({ [e.path]: e.message })),
        R.chain(R.toPairs),
        R.groupBy(R.head),
        R.map(R.pluck(1))
    );

    const formResolver = schema =>
        useCallback(
            async data => {
                const formattedData = R.pipe(
                    emptyStringsToNull,
                    x => diff(state.item, x),
                    x => removeAllUndefined(x)
                )(data);

                const resolvedData = await (async () => {
                    if (isObjectEmpty(formattedData)) {
                        toast("info", 0, noNewDataToSubmitError, showFlag);
                        return {};
                    } else {
                        try {
                            const values = await schema.validate(
                                resolvedData,
                                yupConfig
                            );
                            toast("success", 0, successError, showFlag);
                            return {
                                values,
                                errors: {}
                            };
                        } catch (errors) {
                            const validationErrors = errors?.inner;
                            if (validationErrors) {
                                const errors = formatYupErrors(
                                    validationErrors
                                );
                                return {
                                    values: {},
                                    errors
                                };
                            }
                        }
                    }
                })();

                return {
                    values: {},
                    errors: {},
                    ...resolvedData
                };
            },
            [schema]
        );

    const onSubmit = data => {
        console.log(data);
        submitItem(data);
    };

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        control
    } = useForm({
        resolver: formResolver(yupSchema)
    });

    const cancelHandler = () => {
        history.push("/users");
    };

    const openItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                const data = item.data.data;
                setItem(data);

                const formFields = [
                    "username",
                    "email_address",
                    "first_name",
                    "last_name",
                    "other_names",
                    "additional_notes"
                ];

                for (const key of formFields) {
                    setValue(key, nullToEmptyString(data[key]));
                }
            } catch (error) {}
        })();
    };

    useEffect(openItemAction, []);
    useEffect(schemaAction, []);

    return (
        <Fragment>
            <h3>{state.item?.username}</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <RegisteredField
                    name="username"
                    title="Username"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <RegisteredField
                    name="email_address"
                    title="Email address"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <RegisteredField
                    name="first_name"
                    title="First name"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <RegisteredField
                    name="last_name"
                    title="Last name"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <RegisteredField
                    name="other_names"
                    title="Other names"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <ControlledField
                    name="additional_notes"
                    title="Additional notes"
                    Component={MarkdownTextarea}
                    errors={errors?.additional_notes}
                    control={control}
                />
                <ButtonGroup>
                    <Button appearance="subtle" onClick={cancelHandler}>
                        Cancel
                    </Button>
                    <Button type="submit" appearance="primary">
                        Submit
                    </Button>
                </ButtonGroup>
            </form>
        </Fragment>
    );
};
