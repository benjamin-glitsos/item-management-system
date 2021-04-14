import { useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { joiResolver } from "@hookform/resolvers/joi";
import * as Yup from "yup";
import Joi from "joi";
import Enjoi from "enjoi";

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
                    type: "string"
                },
                first_name: {
                    description: "E.g. Bob",
                    type: "string"
                }
            },
            minProperties: 1
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

    Yup.addMethod(Yup.object, "atLeastOneOf", function (list) {
        return this.test({
            name: "atLeastOneOf",
            message: "${path} must have at least one of these keys: ${keys}",
            exclusive: true,
            params: { keys: list.join(", ") },
            test: value => value == null || list.some(f => value[f] != null)
        });
    });

    const yupSchemaExample = Yup.object().shape({
        username: Yup.string(),
        first_name: Yup.string()
    });

    const yupSchema = buildYup(state.schema, {});

    const joiSchemaExample = Joi.object({
        username: Joi.string().optional().allow(""),
        first_name: Joi.string().optional().allow("")
    }).min(3);

    const joiSchema = Enjoi.schema(state.schema);

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm({
        resolver: yupResolver(yupSchema)
        // resolver: joiResolver(joiSchemaExample)
    });

    const onSubmit = data => console.log(data);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
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
