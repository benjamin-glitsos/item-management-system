import { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { joiResolver } from "@hookform/resolvers/joi";
import * as yup from "yup";
import Joi from "joi";

export default () => {
    // TODO: First check that every feature will work
    // TODO NEXT: use buildYup to use your state.schema in the form
    // TODO NEXT: create the schema/edit-users endpoint and ensure that it pulls in the ref fields properly
    // TODO: add functionality to onSubmit that removes attributes that are blank strings or maybe also other blank properties e.g. []

    const { username } = useParams();

    const defaultState = {
        schema: {
            $schema: "http://json-schema.org/draft-07/schema#",
            title: "Edit Users",
            type: "object",
            properties: {
                username: {
                    description: "E.g. bdole43",
                    type: "string",
                    maxLength: 20,
                    pattern: "^[-_a-zA-Z0-9]*$"
                },
                first_name: {
                    description: "E.g. Bob",
                    type: "string",
                    minLength: 1,
                    maxLength: 50,
                    pattern: "^[^\\s]+(s+[^\\s]+)*$"
                }
            },
            anyOf: [{ required: ["username"] }, { required: ["first_name"] }]
        },
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const apiUrl = "/users/benglitsos";

    const requestItem = () =>
        axios({
            method: "GET",
            url: apiUrl
        });

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
            draft.schema = schema;
        });

    const getItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                setItem(item);
            } catch (error) {}
        })();
    };

    useEffect(getItemAction, []);

    const yupSchema = buildYup(state.schema, {});

    const joiSchema = Joi.object({
        username: Joi.string().required(),
        first_name: Joi.string().required()
    });
    console.log(joiSchema);

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm({
        // resolver: yupResolver(yupSchema)
        resolver: joiResolver(joiSchema)
    });

    const onSubmit = data => console.log(data);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div>{console.log(errors)}</div>
            <input
                {...register("username")}
                defaultValue={state.item?.username}
            />
            <p>{errors.username?.message}</p>

            <input
                {...register("first_name")}
                defaultValue={state.item?.first_name}
            />
            <p>{errors.first_name?.message}</p>

            <input type="submit" />
        </form>
    );
};
