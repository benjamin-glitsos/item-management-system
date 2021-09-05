import { buildYup } from "json-schema-to-yup";

export default maybeSchemaResponse =>
    (schema => {
        return schema ? jsonSchemaToYup(schema) : Yup.object();
    })(maybeSchemaResponse?.data?.data?.data);
