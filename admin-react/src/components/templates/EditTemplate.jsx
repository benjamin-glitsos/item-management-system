import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";
import Form from "modules/Form";
import QueryState from "modules/QueryState";

export default ({ context, children }) => {
    return (
        <Page
            title={context.page.tabTitle}
            description={context.page.pageDescription}
            breadcrumbs={[]}
            maxWidth={context.edit.maxWidth}
        >
            <QueryState
                queries={context.queries}
                maxWidth={context.edit.maxWidth}
            >
                <Grid fluid>
                    <Row>
                        <Col sm={10}>
                            {/* <Form context={context}>{children}</Form> */}
                        </Col>
                        <Col sm={2}>
                            <EditSidebar data={context.itemData} />
                        </Col>
                    </Row>
                </Grid>
            </QueryState>
        </Page>
    );
};
