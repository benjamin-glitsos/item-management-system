import { Fragment, useState, useEffect, useCallback } from "react";
import R from "ramda";
import styled from "styled-components";
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
import ReactMde from "react-mde";
import "react-mde/lib/styles/css/react-mde-all.css";
import ReactMarkdown from "react-markdown";
import { useHistory } from "react-router-dom";
import noNewDataToSubmitError from "%/errors/noNewDataToSubmit";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isBlank from "%/utilities/isBlank";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import toast from "%/utilities/toast";

const Field = ({ name, title, Component, errors, register }) => {
    const id = `Field/${name}`;
    const fieldErrors = errors?.[name];
    return (
        <Fragment>
            <label htmlFor={id}>{title}</label>
            <Component id={id} {...register(name)} />
            {fieldErrors && (
                <ul>
                    {fieldErrors.map((error, i) => (
                        <li key={`Errors/${name}/${i}`}>{error}</li>
                    ))}
                </ul>
            )}
        </Fragment>
    );
};

// TODO: then make ControlledField component as well

export default () => {
    const history = useHistory();

    const { showFlag } = useFlags();

    const { username } = useParams();

    const defaultState = {
        schema: {}
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

    const setInput = input =>
        setState(draft => {
            draft.input = input;
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

    const nullifyEmptyStrings = R.map(x => {
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
                const resolvedData = R.pipe(
                    x => diff(state.item, x),
                    x => removeAllUndefined(x),
                    nullifyEmptyStrings
                )(data);

                try {
                    const values = await schema.validate(
                        resolvedData,
                        yupConfig
                    );
                    return {
                        values,
                        errors: {}
                    };
                } catch (errors) {
                    const validationErrors = errors?.inner;
                    if (validationErrors) {
                        const errors = formatYupErrors(validationErrors);
                        return {
                            values: {},
                            errors
                        };
                    }
                }

                // if (isObjectEmpty(resolvedData)) {
                //     toast(0, noNewDataToSubmitError, showFlag);
                //     return {};
                // } else {
                // }

                // console.log(resolvedData);
                //
                // return {
                //     values: {},
                //     errors: {},
                //     ...resolver()
                // };
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

    const MarkdownTextarea = ({ name, title }) => {
        const [selectedTab, setSelectedTab] = useState("write");
        const errorsList = errors?.additional_notes;
        return (
            <Controller
                control={control}
                name={name}
                render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Fragment>
                        <p>{title}</p>
                        <ReactMde
                            selectedTab={selectedTab}
                            onTabChange={setSelectedTab}
                            generateMarkdownPreview={markdown =>
                                Promise.resolve(
                                    <ReactMarkdown source={markdown} />
                                )
                            }
                            onBlur={onBlur}
                            inputRef={ref}
                            onChange={onChange}
                            value={value}
                        />
                        {errorsList && (
                            <ul>
                                {errorsList.map((error, i) => (
                                    <li key={`Errors/${name}/${i}`}>{error}</li>
                                ))}
                            </ul>
                        )}
                    </Fragment>
                )}
            />
        );
    };
    const cancelHandler = () => {
        history.push("/users");
    };

    const openItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                const data = item.data.data;
                R.forEachObjIndexed((value, key) =>
                    setValue(key, nullToEmptyString(value))
                )(data);
            } catch (error) {}
        })();
    };

    useEffect(openItemAction, []);
    useEffect(schemaAction, []);

    return (
        <Fragment>
            <h3>{state.item?.username}</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Field
                    name="username"
                    title="Username"
                    Component={Textfield}
                    errors={errors}
                    register={register}
                />
                <MarkdownTextarea
                    name="additional_notes"
                    title="Additional Notes"
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

const Label = styled.label`
    ${props => props.isChanged && "font-style: italic;"}
`;

// <Field
//     name="email_address"
//     title="Email address"
//     register={register}
// />
// <Field
//     name="first_name"
//     title="First name"
//     register={register}
// />
// <Field name="last_name" title="Last name" register={register} />
// <Field
//     name="other_names"
//     title="Other names"
//     register={register}
// />
