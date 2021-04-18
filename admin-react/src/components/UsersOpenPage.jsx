import {
    Fragment,
    useEffect,
    useCallback,
    createContext,
    useContext
} from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm, Controller } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import { titleCase } from "title-case";
import config from "%/config";
import axios from "axios";
import { diff } from "deep-object-diff";
import Textfield from "@atlaskit/textfield";
import Button, { ButtonGroup } from "@atlaskit/button";
import { useFlags } from "@atlaskit/flag";
import "react-mde/lib/styles/css/react-mde-all.css";
import { useHistory } from "react-router-dom";
import PageContainer from "%/components/Page/PageContainer";
import OpenLayout from "%/components/OpenLayout";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import noNewDataToSubmitError from "%/errors/noNewDataToSubmit";
import successError from "%/errors/successError";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import toast from "%/utilities/toast";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const history = useHistory();

    const { showFlag } = useFlags();

    const { username } = useParams();

    const defaultState = {
        schema: {},
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const context = useContext(Context);

    const nameSingular = "user";
    const namePlural = "users";
    const keyColumnSingular = "username";
    const keyColumnPlural = "usernames";
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A ${nameSingular} in the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

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
        })
            .then(x => {
                toast("success", 0, successError, showFlag);
            })
            .catch(x => {});

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
            if (x.trim().length === 0) {
                return null;
            } else {
                return x;
            }
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

                try {
                    const values = await schema.validate(
                        formattedData,
                        yupConfig
                    );
                    return {
                        values,
                        errors: {}
                    };
                } catch (errors) {
                    console.log(errors);
                    return {
                        values: {},
                        errors: formatYupErrors(errors.inner)
                    };
                }
            },
            [schema]
        );

    // const formResolver = schema =>
    //     useCallback(
    //         async data => {
    //             const formattedData = R.pipe(
    //                 emptyStringsToNull,
    //                 x => diff(state.item, x),
    //                 x => removeAllUndefined(x)
    //             )(data);
    //
    //             const o = (async () => {
    //                 if (isObjectEmpty(formattedData)) {
    //                     toast("info", 0, noNewDataToSubmitError, showFlag);
    //                     return {};
    //                 } else {
    //                     try {
    //                         const values = await schema.validate(
    //                             resolvedData,
    //                             yupConfig
    //                         );
    //                         return {
    //                             values
    //                         };
    //                     } catch (errors) {
    //                         const validationErrors = errors?.inner;
    //                         if (validationErrors) {
    //                             return {
    //                                 errors: formatYupErrors(validationErrors)
    //                             };
    //                         } else {
    //                             return {};
    //                         }
    //                     }
    //                 }
    //             })();
    //
    //             console.log(o);
    //
    //             return {
    //                 values: {},
    //                 errors: {}
    //             };
    //         },
    //         [schema]
    //     );

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

    const pageContext = {
        nameSingular,
        namePlural,
        title,
        ...pageContainer
    };

    return (
        <Provider value={pageContext}>
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
        </Provider>
    );
};
