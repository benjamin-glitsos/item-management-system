import { useCallback } from "react";
import R from "ramda";
import * as Yup from "yup";
import { buildYup as jsonSchemaToYup } from "json-schema-to-yup";
import { diff } from "deep-object-diff";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import mapObjKeys from "%/utilities/mapObjKeys";
import trimAll from "%/utilities/trimAll";
import emptyStringsToNull from "%/utilities/emptyStringsToNull";
import removeAllUndefined from "%/utilities/removeAllUndefined";

export default validationSchema =>
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
        [validationSchema]
    );

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
