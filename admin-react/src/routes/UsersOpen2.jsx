import { useParams } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { Grid, Row, Col } from "react-flexbox-grid";
import useOpen from "%/hooks/useOpen";
import useOpenClient from "%/hooks/useOpenClient";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import OpenSidebar2 from "%/components/OpenSidebar2";
import OpenForm from "%/components/OpenForm";
import LoadingSpinner from "%/components/LoadingSpinner";

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const open = useOpen();
    const user = useUser();

    const { isLoading, isError, error, data } = useOpenClient({
        path: [user.namePlural, username]
    });

    const { isError, error, mutate: edit, data } = body =>
        useEditClient({
            path: [user.namePlural, username],
            body: {}
        });

    if (isLoading) {
        return (
            <Content maxWidth={open.maxWidth}>
                <LoadingSpinner />
            </Content>
        );
    }

    if (isError) {
        return (
            <Content maxWidth={open.maxWidth}>
                <div>Error</div>
            </Content>
        );
    }

    const openQuery = data.data.data;

    const page = usePage({
        history,
        action: open.action,
        key: openQuery[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

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
                        <OpenForm page={page} open={open} edit={() => edit}>
                            <code>{JSON.stringify(openQuery)}</code>
                        </OpenForm>
                    </Col>
                    <Col sm={2}>
                        <OpenSidebar2 data={openQuery} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
