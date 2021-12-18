import { useCallback } from "react";
import R from "ramda";
import * as yup from "yup";
import { buildYup as jsonSchemaToYup } from "schema-to-yup";
import { diff } from "deep-object-diff";
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

export default ({ schema = emptySchema, data = {} }) => {
    const schemaFields = (() => {
        const maybeSchemaProperties = schema?.properties;

        if (!!maybeSchemaProperties) {
            return Object.keys(maybeSchemaProperties);
        } else {
            return [];
        }
    })();

    const cleanSchema = (() => {
        var sc = schema;

        if (!!sc?.properties) {
            sc.properties = R.map(x => {
                if (x.type instanceof Array) {
                    x.type = x.type.filter(x => x !== "null")[0];
                    x.nullable = true;
                }
                return x;
            })(sc.properties);

            if (!!sc?.required) {
                for (const field of sc.required) {
                    sc.properties[field].required = true;
                }

                delete sc.required;
            }
        }

        return sc;
    })();

    const jsonSchemaToYupConfig = {
        errMessages: (() => {
            const descriptionAttributePattern = /^(?<fieldName>.*)Description$/;

            return Object.fromEntries(
                Object.entries(schema?.properties || []).reduce(
                    (accumulator, current) => {
                        const [key, attributes] = current;

                        const descriptionAttributes = R.pipe(
                            R.pickBy((value, key) =>
                                key.match(descriptionAttributePattern)
                            ),
                            mapObjKeys(
                                key =>
                                    descriptionAttributePattern.exec(key)
                                        ?.groups?.fieldName
                            )
                        )(attributes);

                        const attributesPair =
                            Object.keys(descriptionAttributes).length > 0
                                ? [[key, descriptionAttributes]]
                                : [];

                        return [...accumulator, ...attributesPair];
                    },
                    []
                )
            );
        })()
    };

    const yupSchema = jsonSchemaToYup(cleanSchema, jsonSchemaToYupConfig);

    return useCallback(
        async data => {
            try {
                const cleanData = R.pipe(
                    trimAll,
                    emptyStringsToNull,
                    x => diff(data, x),
                    removeAllUndefined,
                    R.pick(schemaFields)
                )(data);

                const values = await yupSchema.validate(cleanData, yupConfig);

                return {
                    values,
                    errors: {}
                };
            } catch (errors) {
                const cleanErrors = R.pipe(
                    errors =>
                        errors.inner.map(error => {
                            const { message, path, type, value } = error;
                            if (type === "typeError") {
                                return new yup.ValidationError(
                                    [`${path} is required`],
                                    value,
                                    path
                                );
                            } else {
                                return error;
                            }
                        }),
                    R.map(e => ({ [e.path]: e.message })),
                    R.chain(R.toPairs),
                    R.groupBy(R.head),
                    R.map(R.pluck(1))
                )(errors);

                return {
                    values: {},
                    errors: cleanErrors
                };
            }
        },
        [yupSchema]
    );
};

// import { useCallback } from "react";
// import R from "ramda";
// import * as Yup from "yup";
// import { buildYup as schemaToYup } from "schema-to-yup";
// import { diff } from "deep-object-diff";
// import isObjectEmpty from "%/utilities/isObjectEmpty";
// import mapObjKeys from "%/utilities/mapObjKeys";
// import trimAll from "%/utilities/trimAll";
// import emptyStringsToNull from "%/utilities/emptyStringsToNull";
// import removeAllUndefined from "%/utilities/removeAllUndefined";
//
// // export default maybeSchemaResponse =>
// //     (schema => {
// //         return schema ? buildYup(schema) : Yup.object();
// //     })(maybeSchemaResponse?.data?.data?.data);
//
// export default jsonSchema => {
//     const yupConfig = {
//         abortEarly: false
//     };
//
//     function removeMultiTypeConstraints(schema) {
//         if (!schema?.properties) return schema;
//
//         for (const key in schema.properties) {
//             if (key?.type instanceof Array) {
//                 schema.properties[key].type = key.type[0];
//             }
//         }
//
//         // TODO: use this code:
//         //
//         // for (const field in schema.properties) {
//         //     if (schema.properties[field].type instanceof Array) {
//         //         schema.properties[field].type = schema.properties[
//         //             field
//         //         ].type.filter(x => x !== "null")[0];
//         //
//         //         schema.properties[field].nullable = true;
//         //     }
//         // }
//         //
//         // const requiredList = schema?.required;
//         //
//         // if (requiredList) {
//         //     for (const requiredField of requiredList) {
//         //         schema.properties[requiredField].required = true;
//         //     }
//         //
//         //     delete schema.required;
//         // }
//         //
//         // if (namePlural === "users") {
//         //     delete schema.properties.password;
//         // }
//
//         console.log(schema);
//         return schema;
//     }
//
//     const yupSchema = schemaToYup(removeMultiTypeConstraints(jsonSchema), {});
//
//     return useCallback(
//         async data => {
//             try {
//                 const values = await yupSchema.validate(data, yupConfig);
//
//                 return {
//                     values,
//                     errors: {}
//                 };
//             } catch (errors) {
//                 return {
//                     values: {},
//                     errors: errors.inner.reduce(
//                         (allErrors, currentError) => ({
//                             ...allErrors,
//                             [currentError.path]: {
//                                 type: currentError.type ?? "validation",
//                                 message: currentError.message
//                             }
//                         }),
//                         {}
//                     )
//                 };
//             }
//         },
//         [yupSchema]
//     );
// };
//
// // export default ({ jsonSchema = {}, data = {}, useFormProps = {} }) => {
// //     TODO: try adding the json schema as a literal object to here to test
// //     TODO: try reducing this to be simple then building up
// //     TODO: try removing the useCallback hook from this
// //     const yupConfig = {
// //         abortEarly: false
// //     };
// //
// //     const getFormFields = schemaProperties => {
// //         if (!schemaProperties) {
// //             return [];
// //         } else {
// //             return Object.keys(schemaProperties);
// //         }
// //     };
// //
// //     const getErrMessagesFromSchema = () => {
// //         const descriptionAttributePattern = /^(?<fieldName>.*)Description$/;
// //         return Object.fromEntries(
// //             Object.entries(jsonSchema?.properties || []).reduce(
// //                 (accumulator, current) => {
// //                     const [key, attributes] = current;
// //
// //                     const descriptionAttributes = R.pipe(
// //                         R.pickBy((value, key) =>
// //                             key.match(descriptionAttributePattern)
// //                         ),
// //                         mapObjKeys(
// //                             key =>
// //                                 descriptionAttributePattern.exec(key)?.groups
// //                                     ?.fieldName
// //                         )
// //                     )(attributes);
// //
// //                     const attributesPair =
// //                         Object.keys(descriptionAttributes).length > 0
// //                             ? [[key, descriptionAttributes]]
// //                             : [];
// //
// //                     return [...accumulator, ...attributesPair];
// //                 },
// //                 []
// //             )
// //         );
// //     };
// //
// //     const jsonSchemaToYupConfig = {
// //         errMessages: {
// //             ...getErrMessagesFromSchema()
// //         }
// //     };
// //
// //     const yupSchema = isObjectEmpty(jsonSchema)
// //         ? Yup.object()
// //         : jsonSchemaToYup(jsonSchema, jsonSchemaToYupConfig);
// //
// //     const formatYupErrors = R.pipe(
// //         R.map(e => ({ [e.path]: e.message })),
// //         R.chain(R.toPairs),
// //         R.groupBy(R.head),
// //         R.map(R.pluck(1))
// //     );
// //
// //     function resolver() {
// //         useCallback(
// //             async x => {
// //                 const formattedData = R.pipe(
// //                     x => trimAll(x),
// //                     x => emptyStringsToNull(x),
// //                     x => removeAllUndefined(x),
// //                     x => diff(data),
// //                     R.pick(getFormFields(jsonSchema?.properties))
// //                 )(x);
// //
// //                 class Output {
// //                     constructor(values = {}, errors = {}) {
// //                         this.values = values;
// //                         this.errors = errors;
// //                     }
// //                 }
// //
// //                 try {
// //                     const values = await yupSchema.validate(
// //                         formattedData,
// //                         yupConfig
// //                     );
// //                     const formattedValues = emptyStringsToNull(values);
// //                     return new Output(formattedValues);
// //                 } catch (errors) {
// //                     const errorsList = errors.inner.map(error => {
// //                         const { message, path, type, value } = error;
// //                         if (type === "typeError") {
// //                             return new Yup.ValidationError(
// //                                 [`${path} is required`],
// //                                 value,
// //                                 path
// //                             );
// //                         } else {
// //                             return error;
// //                         }
// //                     });
// //                     return {
// //                         ...new Output(),
// //                         errors: formatYupErrors(errorsList)
// //                     };
// //                 }
// //             },
// //             [yupSchema]
// //         );
// //     }
// //
// //     return useForm({
// //         resolver: resolver()
// //     });
// // };
