import { useParams } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Grid, Row, Col } from "react-flexbox-grid";
import useEdit from "%/hooks/useEdit";
import useEditClients from "%/hooks/useEditClients";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import EditSidebar from "%/components/EditSidebar";
import EditForm from "%/components/EditForm";
import LoadingSpinner from "%/components/LoadingSpinner";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const editClients = useEditClients({
        paths: [
            ["schemas", `${edit.action}-${user.namePlural}`],
            [user.namePlural, username]
        ]
    });

    const editClient = body =>
        useEditClient({
            path: [user.namePlural, username],
            body
        });

    if (someProp("isLoading", editClients)) {
        return (
            <Content maxWidth={edit.maxWidth}>
                <LoadingSpinner />
            </Content>
        );
    }

    if (someProp("isError", editClients)) {
        return (
            <Content maxWidth={edit.maxWidth}>
                <ErrorBanner />
            </Content>
        );
    }

    console.log(editClients);

    const [schemaQuery, userQuery] = editClients.map(x => x.data.data.data);

    const page = usePage({
        history,
        action: edit.action,
        key: userQuery[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    // const form = useForm();

    return (
        <Page
            title={page.tabTitle}
            description={page.pageDescription}
            breadcrumbs={[]}
            maxWidth={edit.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>
                        <EditForm page={page} edit={edit}>
                            <code>{JSON.stringify(userQuery)}</code>
                            <code>{JSON.stringify(schemaQuery)}</code>
                        </EditForm>
                    </Col>
                    <Col sm={2}>
                        <EditSidebar data={userQuery} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
