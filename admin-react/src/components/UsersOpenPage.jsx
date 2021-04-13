import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";

export default () => {
    // TODO: First check that every feature will work

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
                email_address: {
                    description: "E.g. bdole@example.com",
                    type: "string",
                    maxLength: 50,
                    pattern:
                        "^[a-zA-Z0-9.!#$%&''*+/=?^_\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
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
                }
            },
            anyOf: [
                { required: ["username"] },
                { required: ["email_address"] },
                { required: ["first_name"] },
                { required: ["last_name"] },
                { required: ["other_names"] },
                { required: ["password"] },
                { required: ["additional_notes"] }
            ]
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

    console.log(state);

    const yupSchema = buildYup(state.schema, {});

    const validation = async () => {
        const x = await yupSchema.isValid(state.item);
        console.log(x);
    };

    console.log(`The username is: ${username}`);

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm();
    const onSubmit = data => console.log(data);

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register("firstName")} /> {/* register an input */}
            <input {...register("lastName", { required: true })} />
            {errors.lastName && <p>Last name is required.</p>}
            <input {...register("age", { pattern: /\d+/ })} />
            {errors.age && <p>Please enter number for age.</p>}
            <input type="submit" />
        </form>
    );
};
