import { useCallback } from "react";
import R from "ramda";
import * as yup from "yup";
import { buildYup as jsonSchemaToYup } from "schema-to-yup";
import { diff } from "deep-object-diff";
import mapObjKeys from "%/utilities/mapObjKeys";
import trimAll from "%/utilities/trimAll";
import emptyStringsToNull from "%/utilities/emptyStringsToNull";
import removeAllUndefined from "%/utilities/removeAllUndefined";

class FormData {
    constructor({ values = {}, errors = {} }) {
        this.values = values;
        this.errors = errors;
    }
}

const yupConfig = {
    abortEarly: false
};

const cleanSchema = schema => {
    var sc = schema;

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

    return sc;
};

const schemaFields = schema => Object.keys(schema.properties);

const schemaToYupConfig = schema => ({
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
                                descriptionAttributePattern.exec(key)?.groups
                                    ?.fieldName
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
});

const yupSchema = schema =>
    jsonSchemaToYup(cleanSchema(schema), schemaToYupConfig(schema));

const cleanData = (schema, data) =>
    R.pipe(
        trimAll
        emptyStringsToNull,
        x => diff(data, x),
        removeAllUndefined,
        R.pick(schemaFields(schema))
    )(data);

const yupSchemaValidate = (schema, data) => {
    return yupSchema(schema).validate(cleanData(schema, data), yupConfig);
};

const cleanErrors = errors =>
    R.pipe(
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

export default schema =>
    useCallback(
        async data => {
            if (schema?.properties) {
                try {
                    return new FormData({
                        values: await yupSchemaValidate(schema, data)
                    });
                } catch (errors) {
                    return new FormData({ errors: cleanErrors(errors) });
                }
            } else {
                return new FormData({});
            }
        },
        [schema]
    );
