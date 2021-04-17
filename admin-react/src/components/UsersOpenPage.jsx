import { Fragment, useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm } from "react-hook-form";
import { buildYup } from "json-schema-to-yup";
import * as Yup from "yup";
import config from "%/config";
import axios from "axios";
import Textfield from "@atlaskit/textfield";
import Button, { ButtonGroup } from "@atlaskit/button";
import groupBy from "group-by";
import ReactMde from "react-mde";
import "react-mde/lib/styles/css/react-mde-all.css";
import ReactMarkdown from "react-markdown";
import fromMaybe from "%/utilities/fromMaybe";
import { useHistory } from "react-router-dom";

export default () => {
    // TODO: integrate MarkdownTextarea into form using register
    // TODO: remove empty strings from the form data. You may need to use the Controller component from react-hook-form. And ensure that empty forms don't submit. And ensure that only changed fields get added to the object, the same fields as default don't get added.
    // TODO: add functionality to onSubmit that removes attributes that are blank strings or maybe also other blank properties e.g. []
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

    const formResolver = validationSchema =>
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
                        errors: groupBy(
                            errors.inner.map(e => ({
                                path: e.path,
                                message: e.message
                            })),
                            "path"
                        )
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
        formState: { errors }
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
                                <li key={`Errors/${name}/${i}`}>
                                    {error.message}
                                </li>
                            ))}
                        </ul>
                    )}
                </Fragment>
            );
        } else {
            return null;
        }
    };

    const MarkdownTextarea = register => {
        const [selectedTab, setSelectedTab] = useState("write");
        if (state.item?.additional_notes) {
            const [value, setValue] = useState(
                fromMaybe(state.item.additional_notes)
            );
            return (
                <ReactMde
                    value={value}
                    onChange={setValue}
                    selectedTab={selectedTab}
                    onTabChange={setSelectedTab}
                    generateMarkdownPreview={markdown =>
                        Promise.resolve(<ReactMarkdown source={markdown} />)
                    }
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
                <MarkdownTextarea register={register} />
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
