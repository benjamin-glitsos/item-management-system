import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Grid, Row, Col } from "react-flexbox-grid";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "%/hooks/useEdit";
import useQueries from "%/hooks/useQueries";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import EditSidebar from "%/components/EditSidebar";
import Form from "%/components/Form";
import LoadingSpinner from "%/components/LoadingSpinner";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";
import nullToEmptyStr from "%/utilities/nullToEmptyStr";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";
import UsersEditForm from "modules/UsersEditForm";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const queries = useQueries([
        ["schemas", `${edit.action}-${user.namePlural}`],
        [user.namePlural, username]
    ]);

    // const editClient = body =>
    //     useEditClient({
    //         path: [user.namePlural, username],
    //         body
    //     });
    //     TODO: make this mutation hook useEditClient again. I accidentally deleted it

    const schema = {
        $schema: "http://json-schema.org/draft-07/schema#",
        type: "object",
        properties: {
            name: {
                description: "Name of the person",
                type: "string"
            },
            email: {
                type: "string",
                format: "email",
                emailDescription: "lalalala",
                maxLength: 50,
                minLength: 1
            },
            fooorbar: {
                type: "string",
                matches: "(foo|bar)"
            },
            age: {
                description: "Age of person",
                type: "number",
                exclusiveMinimum: 0,
                required: true
            },
            characterType: {
                enum: ["good", "bad"],
                enum_titles: ["Good", "Bad"],
                type: "string",
                title: "Type of people",
                propertyOrder: 3
            },
            additionalNotes: {
                maxLength: 1048576,
                type: "string"
            }
        },
        required: ["name", "email"]
    };

    const form = useForm({
        resolver: useYupSchemaResolver(schema)
        // queries[0]
    });

    if (someProp("isLoading", queries)) {
        return (
            <Content maxWidth={edit.maxWidth}>
                <LoadingSpinner />
            </Content>
        );
    }

    if (someProp("isError", queries)) {
        console.log(queries);
        return (
            <Content maxWidth={edit.maxWidth}>
                <ErrorBanner />
            </Content>
        );
    }

    const [schemaQuery, usersQuery] = queries.map(x => x.data.data.data);

    const page = usePage({
        history,
        action: edit.action,
        key: usersQuery[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    for (const [key, value] of Object.entries(usersQuery)) {
        form.setValue(key, nullToEmptyStr(value));
    }

    const context = {
        page,
        edit,
        form,
        schemaQuery,
        usersQuery
    };

    return (
        <UsersEditContext.Provider value={context}>
            <Page
                title={page.tabTitle}
                description={page.pageDescription}
                breadcrumbs={[]}
                maxWidth={edit.maxWidth}
            >
                <Grid fluid>
                    <Row>
                        <Col sm={10}>
                            <UsersEditForm
                                page={page}
                                edit={edit}
                                form={form}
                            />
                            <code>{JSON.stringify(usersQuery)}</code>
                            <code>{JSON.stringify(schemaQuery)}</code>
                        </Col>
                        <Col sm={2}>
                            <EditSidebar data={usersQuery} />
                        </Col>
                    </Row>
                </Grid>
            </Page>
        </UsersEditContext.Provider>
    );
};
