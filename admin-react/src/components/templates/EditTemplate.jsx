import { useContext } from "react";
import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";

export default ({ context, form }) => {
    const cx = useContext(context);
    return (
        <Page
            title={cx.page.tabTitle}
            description={cx.page.pageDescription}
            breadcrumbs={[]}
            maxWidth={cx.edit.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>{form}</Col>
                    <Col sm={2}>
                        <EditSidebar usersQuery={cx.usersQuery} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
