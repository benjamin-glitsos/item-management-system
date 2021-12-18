import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";

export default ({ page, edit, usersQuery, children }) => {
    return (
        <Page
            title={page.tabTitle}
            description={page.pageDescription}
            breadcrumbs={[]}
            maxWidth={edit.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>{children}</Col>
                    <Col sm={2}>
                        <EditSidebar data={usersQuery} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
