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
                setSchema(schema.data);
            } catch (error) {}
        })();
    };

    const openItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                setItem(item.data.data);
            } catch (error) {}
        })();
    };

    useEffect(openItemAction, []);
    useEffect(schemaAction, []);

    const yupConfig = {
        abortEarly: false
    };

    const yupSchema =
        Object.keys(state.schema).length === 0
            ? Yup.object()
            : buildYup(state.schema);

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

    console.log(errors);

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
