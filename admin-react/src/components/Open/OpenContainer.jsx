import { Fragment, useEffect, useCallback } from "react";
import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { useImmer } from "use-immer";
import axios from "axios";
import formatDate from "%/utilities/formatDate";
import toast from "%/utilities/toast";
import config from "%/config";
import * as Yup from "yup";
import { buildYup } from "json-schema-to-yup";
import { diff } from "deep-object-diff";
import "react-mde/lib/styles/css/react-mde-all.css";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import axiosErrorHandler from "%/utilities/axiosErrorHandler";
import noNewDataToSubmitToast from "%/utilities/noNewDataToSubmitToast";
import successToast from "%/utilities/successToast";
import mapObjKeys from "%/utilities/mapObjKeys";

export default ({
    action,
    isCreate,
    key,
    nameSingular,
    namePlural,
    keyField,
    formFields,
    optionalFields
}) => {
    const history = useHistory();

    const defaultState = {
        schema: {},
        item: {},
        opens: 0
    };

    const [state, setState] = useImmer(defaultState);

    const itemUrl = config.serverUrl + `v1/${namePlural}/${key}/`;

    const createUrl = config.serverUrl + `v1/${namePlural}/`;

    const schemaUrl = config.serverUrl + `v1/schemas/${action}-${namePlural}/`;

    const requestItem = () =>
        axios({
            method: "GET",
            url: itemUrl
        }).catch(() => {
            history.replace("/page-not-found");
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: schemaUrl
        }).catch(axiosErrorHandler);

    const submitItem = data => {
        if (!isCreate && isObjectEmpty(data)) {
            noNewDataToSubmitToast();
        } else {
            axios({
                method: isCreate ? "POST" : "PATCH",
                url: isCreate ? createUrl : itemUrl,
                data: {
                    ...data,
                    ...(isCreate && namePlural === "users"
                        ? { password: "DemoPassword" }
                        : {})
                }
            })
                .then(response => {
                    const responseData = response.data.data;
                    successToast();
                    if (isCreate) {
                        history.replace(`/${namePlural}/${data[keyField]}`);
                    } else if (responseData[keyField] !== data[keyField]) {
                        history.replace(
                            `/${namePlural}/${responseData[keyField]}`
                        );
                    } else {
                        setItem(responseData);
                    }
                })
                .catch(axiosErrorHandler);
        }
    };

    const setOpens = () =>
        setState(draft => {
            draft.opens++;
        });

    if (!isCreate && state.opens === 0) {
        setOpens();
    }

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
            for (const field of optionalFields) {
                delete schema.properties[field].anyOf;
                schema.properties[field].type = "string";
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

    const schemaAction = () => {
        (async () => {
            try {
                const schema = await requestSchema();
                setSchema(schema.data);
            } catch (error) {}
        })();
    };

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

    const jsonSchemaToYupConfig = {
        errMessages: {
            ...getErrMessagesFromSchema()
        }
    };

    const yupSchema = isObjectEmpty(state.schema)
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
                    x => diff(state.item, x),
                    x => removeAllUndefined(x)
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
                    return {
                        ...new Output(),
                        errors: formatYupErrors(errors.inner)
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
                try {
                    const item = await requestItem();
                    const data = item.data.data;
                    setItem(data);
                } catch (error) {}
            })();
        }
    };

    const itemValuesAction = () => {
        const data = state.item;
        for (const key of formFields) {
            setValue(key, nullToEmptyString(data[key]));
        }
    };

    const sidebarItems = {
        "Metakey": state.item?.metakey,
        "Created at": formatDate(state.item?.created_at),
        "Edited at": formatDate(state.item?.edited_at),
        "Deleted at": formatDate(state.item?.deleted_at),
        "Opens": state.item?.opens,
        "Edits": state.item?.edits
    };

    useEffect(openItemAction, [state.opens]);
    useEffect(schemaAction, [state.opens]);
    useEffect(itemValuesAction, [state.item]);

    return {
        action,
        isCreate,
        key,
        requestItem,
        requestSchema,
        cancelHandler,
        register,
        handleSubmit,
        setValue,
        errors,
        control,
        onSubmit,
        formFields,
        sidebarItems,
        state
    };
};
