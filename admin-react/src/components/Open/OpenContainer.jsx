import { Fragment, useEffect, useCallback } from "react";
import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { useImmer } from "use-immer";
import axios from "axios";
import { useFlags } from "@atlaskit/flag";
import formatDate from "%/utilities/formatDate";
import toast from "%/utilities/toast";
import config from "%/config";
import * as Yup from "yup";
import { buildYup } from "json-schema-to-yup";
import { diff } from "deep-object-diff";
import "react-mde/lib/styles/css/react-mde-all.css";
import noNewDataToSubmitError from "%/messages/noNewDataToSubmit";
import success from "%/messages/success";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";

export default ({
    action,
    key,
    nameSingular,
    namePlural,
    formFields,
    optionalFields
}) => {
    const history = useHistory();

    const defaultState = {
        schema: {},
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const { showFlag } = useFlags();

    const itemUrl = config.serverUrl + `v1/${namePlural}/${key}/`;

    const schemaUrl = config.serverUrl + `v1/schemas/${action}-${namePlural}/`;

    const requestItem = () =>
        axios({
            method: "GET",
            url: itemUrl
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: schemaUrl
        });

    const submitItem = data => {
        if (isObjectEmpty(data)) {
            toast("info", 0, noNewDataToSubmitError, showFlag);
        } else {
            axios({
                method: "PATCH",
                url: itemUrl,
                data
            })
                .then(response => {
                    const responseData = response.data.data;
                    toast("success", 0, success, showFlag);
                    if (responseData[key] !== state.item[key]) {
                        history.replace(`/${namePlural}/${responseData[key]}`);
                    } else {
                        setItem(responseData);
                    }
                })
                .catch(() => {
                    toast("error", 0, success, showFlag);
                });
        }
    };

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

    const yupSchema = isObjectEmpty(state.schema)
        ? Yup.object()
        : buildYup(state.schema);

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
                    emptyStringsToNull,
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
                    return {
                        ...new Output(),
                        values
                    };
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
        (async () => {
            try {
                const item = await requestItem();
                const data = item.data.data;
                setItem(data);
            } catch (error) {}
        })();
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

    useEffect(openItemAction, []);
    useEffect(itemValuesAction, [state.item]);
    useEffect(schemaAction, []);

    return {
        action,
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
