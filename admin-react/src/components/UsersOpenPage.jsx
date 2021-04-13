import { useParams } from "react-router-dom";
import { buildYup } from "json-schema-to-yup";

export default () => {
    const { username } = useParams();
    // TODO: First check that every feature will work
    // TODO: Start with json-schema-to-yup

    const jsonSchema = {
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
    };

    const yupConfig = {};

    const yupSchema = buildYup(jsonSchema, yupConfig);
    console.log(yupSchema);

    const validation = async () => {
        const x = await yupSchema.isValid({
            username: "wow",
            email_address: "lalalala"
        });
        console.log(x);
    };

    validation();

    return `The username is: ${username}`;
};
