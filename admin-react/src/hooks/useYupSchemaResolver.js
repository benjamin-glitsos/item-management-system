import { useCallback } from "react";
import R from "ramda";
import * as Yup from "yup";
import { buildYup as jsonSchemaToYup } from "schema-to-yup";
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

export default ({ schemaData, originalData }) => {
    const schema = schemaData || emptySchema;
    const original = originalData || {};

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
                    x => diff(original, x),
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
                                return new Yup.ValidationError(
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
                console.log(cleanErrors);

                return {
                    values: {},
                    errors: cleanErrors
                };
            }
        },
        [yupSchema]
    );
};
