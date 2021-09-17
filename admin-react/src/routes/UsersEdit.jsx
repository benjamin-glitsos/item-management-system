import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Grid, Row, Col } from "react-flexbox-grid";
import Textfield from "@atlaskit/textfield";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "%/hooks/useEdit";
import useQueriesClient from "%/hooks/useQueriesClient";
import useMutationClient from "%/hooks/useMutationClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import EditSidebar from "%/components/EditSidebar";
import Form from "%/components/Form";
import LoadingSpinner from "%/components/LoadingSpinner";
import RegisteredField2 from "%/components/RegisteredField2";
import ControlledField2 from "%/components/ControlledField2";
import FormSubheading from "%/components/FormSubheading";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";
import nullToEmptyStr from "%/utilities/nullToEmptyStr";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const data = useQueriesClient([
        ["schemas", `${edit.action}-${user.namePlural}`],
        [user.namePlural, username]
    ]);

    const handleEdit = body =>
        useMutationClient({
            path: [user.namePlural, username],
            body
        });

    const form = useForm({
        resolver: useYupSchemaResolver(data[0])
    });

    if (someProp("isLoading", data)) {
        return <LoadingSpinner />;
    }

    if (someProp("isError", data)) {
        return <ErrorBanner />;
    }

    const [schemaData, usersData] = data.map(x => x.data.data.data);

    const page = usePage({
        history,
        action: edit.action,
        key: usersData[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    for (const [key, value] of Object.entries(usersData)) {
        form.setValue(key, nullToEmptyStr(value));
    }

    const context = {
        page,
        edit,
        form,
        schemaData,
        usersData
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
                            <Form>
                                <FormSubheading level={3}>
                                    Details
                                </FormSubheading>
                                <RegisteredField2
                                    name="username"
                                    title="Username"
                                    Component={Textfield}
                                    columnWidths={{ lg: 6 }}
                                />
                                <RegisteredField2
                                    name="email_address"
                                    title="Email address"
                                    Component={Textfield}
                                    columnWidths={{ lg: 6 }}
                                />
                                <RegisteredField2
                                    name="first_name"
                                    title="First name"
                                    Component={Textfield}
                                    columnWidths={{ lg: 4 }}
                                />
                                <RegisteredField2
                                    name="last_name"
                                    title="Last name"
                                    Component={Textfield}
                                    columnWidths={{ lg: 4 }}
                                />
                                <RegisteredField2
                                    name="other_names"
                                    title="Other names"
                                    Component={Textfield}
                                    columnWidths={{ lg: 4 }}
                                />
                                <FormSubheading level={3}>Misc.</FormSubheading>
                                <ControlledField2
                                    name="additional_notes"
                                    title="Additional notes"
                                    Component={MarkdownTextarea}
                                    columnWidths={{ sm: 12 }}
                                />
                                <code>{JSON.stringify(usersData)}</code>
                                <code>{JSON.stringify(schemaData)}</code>
                            </Form>
                        </Col>
                        <Col sm={2}>
                            <EditSidebar data={usersData} />
                        </Col>
                    </Row>
                </Grid>
            </Page>
        </UsersEditContext.Provider>
    );
};
