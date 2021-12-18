import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";

export default ({ page, edit, sidebarData, form }) => {
    return (
        <Page
            title={page.tabTitle}
            description={page.pageDescription}
            breadcrumbs={[]}
            maxWidth={edit.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>{form}</Col>
                    <Col sm={2}>
                        <EditSidebar data={sidebarData} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
