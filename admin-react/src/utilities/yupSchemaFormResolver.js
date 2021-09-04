import R from "ramda";
import * as Yup from "yup";
import { buildYup } from "json-schema-to-yup";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import mapObjKeys from "%/utilities/mapObjKeys";

export default jsonSchema => {
    const getErrMessagesFromSchema = () => {
        const descriptionAttributePattern = /^(?<fieldName>.*)Description$/;
        return Object.fromEntries(
            Object.entries(jsonSchema?.properties || []).reduce(
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
    };

    const jsonSchemaToYupConfig = {
        errMessages: {
            ...getErrMessagesFromSchema()
        }
    };

    const yupSchema = isObjectEmpty(jsonSchema)
        ? Yup.object()
        : buildYup(jsonSchema, jsonSchemaToYupConfig);

    const formResolver = schema =>
        useCallback(
            async data => {
                const formattedData = R.pipe(
                    x => trimAll(x),
                    x => emptyStringsToNull(x),
                    x => removeAllUndefined(x),
                    x => diff(state.item, x),
                    R.pick(getFormFields(jsonSchema?.properties))
                )(data);

                class Output {
                    constructor(values = {}, errors = {}) {
                        this.values = values;
                        this.errors = errors;
                    }
                }

                try {
                    const values = await schema.validate(
                        formattedData,
                        yupConfig
                    );
                    const formattedValues = emptyStringsToNull(values);
                    return new Output(formattedValues);
                } catch (errors) {
                    const errorsList = errors.inner.map(error => {
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
                    });
                    return {
                        ...new Output(),
                        errors: formatYupErrors(errorsList)
                    };
                }
            },
            [schema]
        );

    return formResolver(yupSchema);
};
