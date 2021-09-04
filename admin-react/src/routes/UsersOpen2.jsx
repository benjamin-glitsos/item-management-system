import { useParams } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { Grid, Row, Col } from "react-flexbox-grid";
import useOpen from "%/hooks/useOpen";
import useOpenService from "%/hooks/useOpenService";
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

    const { isLoading, isError, error, data } = useOpenService({
        path: [user.namePlural, username]
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

    const openService = data.data.data;

    const page = usePage({
        history,
        action: open.action,
        key: openService[user.keyField],
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
                        <OpenForm page={page} open={open}>
                            <code>{JSON.stringify(openService)}</code>
                        </OpenForm>
                    </Col>
                    <Col sm={2}>
                        <OpenSidebar2 data={openService} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
