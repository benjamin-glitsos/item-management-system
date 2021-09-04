import { useParams } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Grid, Row, Col } from "react-flexbox-grid";
import useOpen from "%/hooks/useOpen";
import useOpenClients from "%/hooks/useOpenClients";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import OpenSidebar2 from "%/components/OpenSidebar2";
import OpenForm from "%/components/OpenForm";
import LoadingSpinner from "%/components/LoadingSpinner";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const open = useOpen();
    const user = useUser();

    const openClients = useOpenClients({
        paths: [
            ["schemas", `edit-${user.namePlural}`],
            [user.namePlural, username]
        ]
    });

    // const editClient = body =>
    //     useEditClient({
    //         path: [user.namePlural, username],
    //         body
    //     });

    if (someProp("isLoading", openClients)) {
        return (
            <Content maxWidth={open.maxWidth}>
                <LoadingSpinner />
            </Content>
        );
    }

    if (someProp("isError", openClients)) {
        return (
            <Content maxWidth={open.maxWidth}>
                <ErrorBanner />
            </Content>
        );
    }

    const [schemaQuery, userQuery] = openClients.map(x => x.data.data.data);

    const page = usePage({
        history,
        action: open.action,
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
            maxWidth={open.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>
                        <OpenForm page={page} open={open}>
                            <code>{JSON.stringify(userQuery)}</code>
                            <code>{JSON.stringify(schemaQuery)}</code>
                        </OpenForm>
                    </Col>
                    <Col sm={2}>
                        <OpenSidebar2 data={userQuery} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
