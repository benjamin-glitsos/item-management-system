import { useEffect, useCallback } from "react";
import R from "ramda";
import { useHistory, useLocation } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useImmer } from "use-immer";
import config from "%/config";
import * as Yup from "yup";
import { buildYup } from "json-schema-to-yup";
import { diff } from "deep-object-diff";
import "react-mde/lib/styles/css/react-mde-all.css";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import noNewDataToSubmitToast from "%/utilities/noNewDataToSubmitToast";
import successToast from "%/utilities/successToast";
import prettyQuote from "%/utilities/prettyQuote";
import mapObjKeys from "%/utilities/mapObjKeys";
import joinPath from "%/utilities/joinPath";
import axiosErrorHandler from "%/utilities/axiosErrorHandler";
import { openService, createService, editService } from "%/services";

export default ({
    action,
    isCreate,
    key,
    nameSingular,
    namePlural,
    keyField
}) => {
    const history = useHistory();
    const location = useLocation();

    const defaultState = {
        schema: {},
        item: {},
        refreshes: 0,
        isLoading: true
    };

    const [state, setState] = useImmer(defaultState);

    const itemUrl = joinPath([namePlural, key]);

    const createUrl = config.serverUrl + `v1/${namePlural}/`;

    const schemaUrl = joinPath(["schemas", `${action}-${namePlural}/`]);

    const requestItem = () =>
        openService({ path: itemUrl })
            .then(x => x.data.data)
            .catch(() => {
                history.replace("/page-not-found");
            });

    const requestSchema = () =>
        openService({ path: schemaUrl })
            .then(x => x.data)
            .catch(axiosErrorHandler);

    const submitItem = data => {
        if (data && state.schema.minProperties > 0) {
            for (const key of getFormFields(state.schema.properties)) {
                setValue(key, nullToEmptyString(state.item[key]));
            }
            noNewDataToSubmitToast();
        } else {
            if (isCreate) {
                createService({
                    path: namePlural,
                    body: {
                        ...data,
                        ...(namePlural === "users"
                            ? { password: "DemoPassword" }
                            : {})
                    }
                })
                    .then(response => {
                        history.replace(`/${namePlural}/${data[keyField]}`);
                        successToast({
                            title: "Successfully created",
                            description: `The ${nameSingular} ${prettyQuote(
                                data[keyField]
                            )} was created.`
                        });
                    })
                    .catch(axiosErrorHandler);
            } else {
                editService({ path: itemUrl, body: data })
                    .then(response => {
                        const responseData = response.data.data[0];
                        setItem(responseData);
                        if (state.item[keyField] !== responseData[keyField]) {
                            history.replace(
                                `/${namePlural}/${responseData[keyField]}`
                            );
                        }
                        successToast({
                            title: "Saved",
                            description: "Successfully edited"
                        });
                    })
                    .catch(axiosErrorHandler);
            }
        }
    };

    const setLoading = bool =>
        setState(draft => {
            draft.isLoading = bool;
        });

    const setRefreshes = () =>
        setState(draft => {
            draft.refreshes++;
        });

    if (!isCreate && state.refreshes === 0) {
        setRefreshes();
    }

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
            for (const field in schema.properties) {
                if (schema.properties[field].type instanceof Array) {
                    schema.properties[field].type = schema.properties[
                        field
                    ].type.filter(x => x !== "null")[0];

                    schema.properties[field].nullable = true;
                }
            }

            const requiredList = schema?.required;

            if (requiredList) {
                for (const requiredField of requiredList) {
                    schema.properties[requiredField].required = true;
                }

                delete schema.required;
            }

            if (namePlural === "users") {
                delete schema.properties.password;
            }

            draft.schema = schema;
        });

    const yupConfig = {
        abortEarly: false
    };

    const getErrMessagesFromSchema = () => {
        const descriptionAttributePattern = /^(?<fieldName>.*)Description$/;
        return Object.fromEntries(
            Object.entries(state.schema?.properties || []).reduce(
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

    const getFormFields = schemaProperties => {
        if (!schemaProperties) {
            return [];
        } else {
            return Object.keys(schemaProperties);
        }
    };

    const jsonSchemaToYupConfig = {
        errMessages: {
            ...getErrMessagesFromSchema()
        }
    };

    const yupSchema = state.schema
        ? Yup.object()
        : buildYup(state.schema, jsonSchemaToYupConfig);

    const emptyStringsToNull = R.map(x => {
        if (typeof x === "string") {
            if (x.trim().length === 0) {
                return null;
            } else {
                return x;
            }
        } else {
            return x;
        }
    });

    const trimAll = R.map(x => {
        if (typeof x === "string") {
            return x.trim();
        } else {
            return x;
        }
    });

    const nullToEmptyString = x => (x === null ? "" : x);

    const formatYupErrors = R.pipe(
        R.map(e => ({ [e.path]: e.message })),
        R.chain(R.toPairs),
        R.groupBy(R.head),
        R.map(R.pluck(1))
    );

    const formResolver = schema =>
        useCallback(
            async data => {
                const formattedData = R.pipe(
                    x => trimAll(x),
                    x => emptyStringsToNull(x),
                    x => removeAllUndefined(x),
                    x => diff(state.item, x),
                    R.pick(getFormFields(state.schema?.properties))
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

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        control
    } = useForm({
        resolver: formResolver(yupSchema)
    });

    const cancelHandler = () => {
        history.push(`/${namePlural}`);
    };

    const onSubmit = data => submitItem(data);

    const openItemAction = () => {
        if (!isCreate) {
            (async () => {
                setLoading(true);
                const item = await requestItem();
                const data = item.data.data;
                setItem(data);
                setLoading(false);
            })();
        }
    };

    const openAction = () => {
        (async () => {
            setLoading(true);
            if (isCreate) {
                const schema = await requestSchema();
                setSchema(schema);
            } else {
                const [schema, item] = await Promise.all([
                    requestSchema(),
                    requestItem()
                ]);
                setSchema(schema);
                if (item) {
                    setItem(item);
                    for (const key of getFormFields(schema.properties)) {
                        setValue(key, nullToEmptyString(item[key]));
                    }
                }
            }
            setLoading(false);
        })();
    };

    useEffect(openAction, [state.refreshes, location]);

    return {
        action,
        isCreate,
        key,
        cancelHandler,
        register,
        handleSubmit,
        setValue,
        errors,
        control,
        onSubmit,
        state
    };
};
