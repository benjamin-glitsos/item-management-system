import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";
import Form from "modules/Form";

export default ({ context, form }) => {
    return (
        <Page
            title={context.page.tabTitle}
            description={context.page.pageDescription}
            breadcrumbs={[]}
            maxWidth={context.edit.maxWidth}
        >
            <Grid fluid>
                <Row>
                    <Col sm={10}>
                        <Form context={context}>{form}</Form>
                    </Col>
                    <Col sm={2}>
                        <EditSidebar data={context.data} />
                    </Col>
                </Row>
            </Grid>
        </Page>
    );
};
