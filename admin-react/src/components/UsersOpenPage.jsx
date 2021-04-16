import { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import config from "%/config";
import axios from "axios";

export default () => {
    // TODO: First check that every feature will work
    // TODO: remove empty strings from the form data. You may need to use the Controller component from react-hook-form. And ensure that empty forms don't submit. And ensure that only changed fields get added to the object, the same fields as default don't get added.
    // TODO NEXT: create the schema/edit-users endpoint and ensure that it pulls in the ref fields properly
    // TODO: add functionality to onSubmit that removes attributes that are blank strings or maybe also other blank properties e.g. []

    const { username } = useParams();

    const defaultState = {
        schema: {
            $schema: "http://json-schema.org/draft-07/schema#",
            title: "Create Users",
            type: "object",
            properties: {
                username: {
                    description: "E.g. bdole43",
                    type: "string",
                    maxLength: 20,
                    pattern: "^[-_a-zA-Z0-9]*$"
                },
                email_address: {
                    description: "E.g. bdole@example.com",
                    type: "string",
                    maxLength: 50,
                    format: "email"
                },
                first_name: {
                    description: "E.g. Bob",
                    type: "string",
                    minLength: 1,
                    maxLength: 50,
                    pattern: "^[^\\s]+(s+[^\\s]+)*$"
                },
                last_name: {
                    description: "E.g. Dole",
                    type: "string",
                    minLength: 1,
                    maxLength: 70,
                    pattern: "^[^\\s]+(s+[^\\s]+)*$"
                },
                other_names: {
                    description:
                        "Middle names and any other names that a person may possess. E.g. James William",
                    type: "string",
                    minLength: 1,
                    maxLength: 120,
                    pattern: "^[^\\s]+(s+[^\\s]+)*$"
                },
                password: {
                    description: "A strong, secure password.",
                    type: "string",
                    minLength: 1,
                    maxLength: 40,
                    pattern: "^[^\\s]+(s+[^\\s]+)*$"
                },
                additional_notes: {
                    type: "string",
                    maxLength: 1048576,
                    pattern: "[\\s\\S]*\\S[\\s\\S]*"
                }
            },
            required: [
                "username",
                "email_address",
                "first_name",
                "last_name",
                "other_names",
                "password"
            ]
        },
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    // const requestItem = () =>
    //     axios({
    //         method: "GET",
    //         url: config.serverUrl + `/users/${username}`
    //     });

    console.log(config.serverUrl + "v1/schemas/edit-users");

    const requestSchema = () =>
        axios({
            method: "GET",
            url: config.serverUrl + "v1/schemas/edit-users"
        });

    requestSchema().then(console.log);

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
            draft.schema = schema;
        });

    const schemaAction = () => {
        (async () => {
            try {
                const schema = await requestSchema();
                setSchema(schema);
            } catch (error) {}
        })();
    };

    // const openItemAction = () => {
    //     (async () => {
    //         try {
    //             const item = await requestItem();
    //             setItem(item);
    //         } catch (error) {}
    //     })();
    // };

    useEffect(schemaAction, []);
    // useEffect(openItemAction, []);

    const yupConfig = {
        abortEarly: false
    };

    const yupSchemaExample = Yup.object().shape({
        username: Yup.string()
            .min(8)
            .required()
            .matches(RegExp("(.*[a-z].*)"), "Lowercase")
            .matches(RegExp("(.*[A-Z].*)"), "Uppercase")
            .matches(RegExp("(.*\\d.*)"), "Number")
            .matches(RegExp('[!@#$%^&*(),.?":{}|<>]'), "Special"),
        first_name: Yup.string().required()
    });

    const yupSchema = buildYup(state.schema);

    const formResolver = validationSchema =>
        useCallback(
            async data => {
                try {
                    const values = await validationSchema.validate(data, {
                        abortEarly: false
                    });

                    return {
                        values,
                        errors: {}
                    };
                } catch (errors) {
                    return {
                        values: {},
                        // TODO: map errors into the format: { keys: [error_messages] }
                        errors
                    };
                }
            },
            [validationSchema]
        );

    // errors: errors.inner.reduce(
    //     (allErrors, currentError) => ({
    //         ...allErrors,
    //         [currentError.path]: {
    //             type: currentError.type ?? "validation",
    //             message: currentError.message
    //         }
    //     }),
    //     {}
    // );

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm({
        resolver: formResolver(yupSchema)
    });

    const onSubmit = data => console.log(data);

    // console.log(errors);

    return (
        <div>
            <p>
                <b>{username}</b>
            </p>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input
                    {...register("username")}
                    defaultValue={state.item?.username}
                />

                <input
                    {...register("first_name")}
                    defaultValue={state.item?.first_name}
                />

                <input type="submit" />
            </form>
        </div>
    );
};
// TODO: map the multiple errors to the <li>. Once yup's abortEarly:false is used, then you can do this

// <ul>
//     <li>{errors?.first_name?.message}</li>
// </ul>
// <ul>
//     <li>{errors?.username?.message}</li>
// </ul>
