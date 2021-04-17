import { Fragment, useState, useEffect, useCallback } from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm, Controller } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import config from "%/config";
import axios from "axios";
import { diff } from "deep-object-diff";
import Textfield from "@atlaskit/textfield";
import Button, { ButtonGroup } from "@atlaskit/button";
import ReactMde from "react-mde";
import "react-mde/lib/styles/css/react-mde-all.css";
import ReactMarkdown from "react-markdown";
import fromMaybe from "%/utilities/fromMaybe";
import { useHistory } from "react-router-dom";

export default () => {
    const history = useHistory();

    const { username } = useParams();

    const defaultState = {
        schema: {},
        item: {}
    };

    const [state, setState] = useImmer(defaultState);

    const requestItem = () =>
        axios({
            method: "GET",
            url: config.serverUrl + `v1/users/${username}/`
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: config.serverUrl + "v1/schemas/edit-users/"
        });

    const submitItem = data =>
        axios({
            method: "PATCH",
            url: config.serverUrl + `v1/users/${username}/`,
            data
        });

    const setItem = item =>
        setState(draft => {
            draft.item = item;
        });

    const setSchema = schema =>
        setState(draft => {
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

    const openItemAction = () => {
        (async () => {
            try {
                const item = await requestItem();
                setItem(item.data.data);
            } catch (error) {}
        })();
    };

    useEffect(openItemAction, []);
    useEffect(schemaAction, []);

    const yupConfig = {
        abortEarly: false
    };

    const yupSchema =
        Object.keys(state.schema).length === 0
            ? Yup.object()
            : buildYup(state.schema);

    const groupByKey = R.groupWith((a, b) => R.head(a) === R.head(b));

    const formResolver = validationSchema =>
        useCallback(
            async data => {
                const resolvedData = R.pipe(
                    R.map(x => {
                        if (typeof x === "string") {
                            return x.trim() === "" ? null : x;
                        } else {
                            return x;
                        }
                    }),
                    x => diff(state.item, x)
                )(data);
                console.log(resolvedData);
                try {
                    const values = await validationSchema.validate(
                        resolvedData,
                        {
                            abortEarly: false
                        }
                    );

                    return {
                        values,
                        errors: {}
                    };
                } catch (errors) {
                    return {
                        values: {},
                        errors: R.pipe(
                            R.prop("inner"),
                            R.map(e => ({
                                [e.path]: e.message
                            })),
                            R.chain(R.toPairs),
                            R.groupBy(R.head),
                            R.map(R.pluck(1))
                        )(errors)
                    };
                }
            },
            [validationSchema]
        );

    const onSubmit = data => {
        console.log(data);
        submitItem(data);
    };

    const {
        register,
        handleSubmit,
        formState: { errors },
        control
    } = useForm({
        resolver: formResolver(yupSchema)
    });

    const TextField = ({ name, title, register }) => {
        const id = `Field/${name}`;
        if (state.item[name]) {
            const errorsList = errors?.[name];
            return (
                <Fragment>
                    <label htmlFor={id}>{title}</label>
                    <Textfield
                        id={id}
                        {...register(name)}
                        defaultValue={state.item[name]}
                    />
                    {errorsList && (
                        <ul>
                            {errorsList.map((error, i) => (
                                <li key={`Errors/${name}/${i}`}>{error}</li>
                            ))}
                        </ul>
                    )}
                </Fragment>
            );
        } else {
            return null;
        }
    };

    const MarkdownTextarea = ({ name, title }) => {
        const [selectedTab, setSelectedTab] = useState("write");
        const additionalNotes = state.item?.additional_notes;
        if (additionalNotes) {
            const defaultValue = state.item?.additional_notes || "";
            return (
                <Controller
                    control={control}
                    name={name}
                    defaultValue={defaultValue}
                    render={({ field: { onChange, onBlur, value, ref } }) => (
                        <Fragment>
                            <p>{title}</p>
                            <ReactMde
                                selectedTab={selectedTab}
                                onTabChange={setSelectedTab}
                                generateMarkdownPreview={markdown =>
                                    Promise.resolve(
                                        <ReactMarkdown source={markdown} />
                                    )
                                }
                                onBlur={onBlur}
                                inputRef={ref}
                                onChange={onChange}
                                value={value}
                            />
                        </Fragment>
                    )}
                />
            );
        } else {
            return null;
        }
    };
    const cancelHandler = () => {
        history.push("/users");
    };

    return (
        <Fragment>
            <h3>{state.item?.username}</h3>
            <form onSubmit={handleSubmit(onSubmit)}>
                <TextField
                    name="username"
                    title="Username"
                    register={register}
                />
                <TextField
                    name="email_address"
                    title="Email address"
                    register={register}
                />
                <TextField
                    name="first_name"
                    title="First name"
                    register={register}
                />
                <TextField
                    name="last_name"
                    title="Last name"
                    register={register}
                />
                <TextField
                    name="other_names"
                    title="Other names"
                    register={register}
                />
                <MarkdownTextarea
                    name="additional_notes"
                    title="Additional Notes"
                />
                <ButtonGroup>
                    <Button appearance="subtle" onClick={cancelHandler}>
                        Cancel
                    </Button>
                    <Button type="submit" appearance="primary">
                        Submit
                    </Button>
                </ButtonGroup>
            </form>
        </Fragment>
    );
};
