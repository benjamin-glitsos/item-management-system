import { useCallback } from "react";
import R from "ramda";
import * as Yup from "yup";
import { buildYup as schemaToYup } from "schema-to-yup";
import { diff } from "deep-object-diff";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import mapObjKeys from "%/utilities/mapObjKeys";
import trimAll from "%/utilities/trimAll";
import emptyStringsToNull from "%/utilities/emptyStringsToNull";
import removeAllUndefined from "%/utilities/removeAllUndefined";

const emptySchema = {
    $schema: "http://json-schema.org/draft-07/schema#",
    title: "N/A",
    type: "object",
    properties: {}
};

const yupConfig = {
    abortEarly: false
};

const schemaToYupConfig = {};

export default schemaQueryData => {
    const data = schemaQueryData?.data?.data?.data || emptySchema;
    const schema = data;

    const formattedSchema = (() => {
        if (!!schema?.properties) {
            schema.properties = R.map(x => {
                if (x.type instanceof Array) {
                    x.type = x.type.filter(x => x !== "null")[0];
                    x.nullable = true;
                }
                return x;
            })(schema.properties);

            if (!!schema?.required) {
                for (const field of schema.required) {
                    schema.properties[field].required = true;
                }

                delete schema.required;
            }

            if (!!schema?.title) {
                if (schema.title.match(/users/i)) {
                    delete schema.properties.password;
                }
            }
        }
        console.log(schema);

        return schema;
    })();

    const yupSchema = schemaToYup(formattedSchema, schemaToYupConfig);

    return useCallback(
        async data => {
            try {
                const values = await yupSchema.validate(data, yupConfig);

                return {
                    values,
                    errors: {}
                };
            } catch (errors) {
                return {
                    values: {},
                    errors: errors.inner.reduce(
                        (allErrors, currentError) => ({
                            ...allErrors,
                            [currentError.path]: {
                                type: currentError.type ?? "validation",
                                message: currentError.message
                            }
                        }),
                        {}
                    )
                };
            }
        },
        [yupSchema]
    );
};

// export default ({ jsonSchema = {}, data = {}, useFormProps = {} }) => {
//     TODO: try adding the json schema as a literal object to here to test
//     TODO: try reducing this to be simple then building up
//     TODO: try removing the useCallback hook from this
//     const yupConfig = {
//         abortEarly: false
//     };
//
//     const getFormFields = schemaProperties => {
//         if (!schemaProperties) {
//             return [];
//         } else {
//             return Object.keys(schemaProperties);
//         }
//     };
//
//     const getErrMessagesFromSchema = () => {
//         const descriptionAttributePattern = /^(?<fieldName>.*)Description$/;
//         return Object.fromEntries(
//             Object.entries(jsonSchema?.properties || []).reduce(
//                 (accumulator, current) => {
//                     const [key, attributes] = current;
//
//                     const descriptionAttributes = R.pipe(
//                         R.pickBy((value, key) =>
//                             key.match(descriptionAttributePattern)
//                         ),
//                         mapObjKeys(
//                             key =>
//                                 descriptionAttributePattern.exec(key)?.groups
//                                     ?.fieldName
//                         )
//                     )(attributes);
//
//                     const attributesPair =
//                         Object.keys(descriptionAttributes).length > 0
//                             ? [[key, descriptionAttributes]]
//                             : [];
//
//                     return [...accumulator, ...attributesPair];
//                 },
//                 []
//             )
//         );
//     };
//
//     const jsonSchemaToYupConfig = {
//         errMessages: {
//             ...getErrMessagesFromSchema()
//         }
//     };
//
//     const yupSchema = isObjectEmpty(jsonSchema)
//         ? Yup.object()
//         : jsonSchemaToYup(jsonSchema, jsonSchemaToYupConfig);
//
//     const formatYupErrors = R.pipe(
//         R.map(e => ({ [e.path]: e.message })),
//         R.chain(R.toPairs),
//         R.groupBy(R.head),
//         R.map(R.pluck(1))
//     );
//
//     function resolver() {
//         useCallback(
//             async x => {
//                 const formattedData = R.pipe(
//                     x => trimAll(x),
//                     x => emptyStringsToNull(x),
//                     x => removeAllUndefined(x),
//                     x => diff(data),
//                     R.pick(getFormFields(jsonSchema?.properties))
//                 )(x);
//
//                 class Output {
//                     constructor(values = {}, errors = {}) {
//                         this.values = values;
//                         this.errors = errors;
//                     }
//                 }
//
//                 try {
//                     const values = await yupSchema.validate(
//                         formattedData,
//                         yupConfig
//                     );
//                     const formattedValues = emptyStringsToNull(values);
//                     return new Output(formattedValues);
//                 } catch (errors) {
//                     const errorsList = errors.inner.map(error => {
//                         const { message, path, type, value } = error;
//                         if (type === "typeError") {
//                             return new Yup.ValidationError(
//                                 [`${path} is required`],
//                                 value,
//                                 path
//                             );
//                         } else {
//                             return error;
//                         }
//                     });
//                     return {
//                         ...new Output(),
//                         errors: formatYupErrors(errorsList)
//                     };
//                 }
//             },
//             [yupSchema]
//         );
//     }
//
//     return useForm({
//         resolver: resolver()
//     });
// };

// const schema = {
//     $schema: "http://json-schema.org/draft-07/schema#",
//     type: "object",
//     properties: {
//         name: {
//             description: "Name of the person",
//             type: "string"
//         },
//         email: {
//             type: "string",
//             format: "email",
//             emailDescription: "lalalala",
//             maxLength: 50,
//             minLength: 1
//         },
//         fooorbar: {
//             type: "string",
//             matches: "(foo|bar)"
//         },
//         age: {
//             description: "Age of person",
//             type: "number",
//             exclusiveMinimum: 0,
//             required: true
//         },
//         characterType: {
//             enum: ["good", "bad"],
//             enum_titles: ["Good", "Bad"],
//             type: "string",
//             title: "Type of people",
//             propertyOrder: 3
//         },
//         additionalNotes: {
//             maxLength: 1048576,
//             type: "string"
//         }
//     },
//     required: ["name", "email"]
// };

// export default maybeSchemaResponse =>
//     (schema => {
//         return schema ? buildYup(schema) : Yup.object();
//     })(maybeSchemaResponse?.data?.data?.data);
