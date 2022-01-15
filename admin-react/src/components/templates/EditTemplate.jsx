import { createContext } from "react";
import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";
import EditForm from "modules/EditForm";
import QueryState from "modules/QueryState";

export const EditContext = createContext();

export default ({ context, children }) => (
    <Page
        title={context.page.tabTitle}
        description={context.page.pageDescription}
        breadcrumbs={[]}
        maxWidth={context.edit.maxWidth}
    >
        <QueryState queries={context.queries} maxWidth={context.edit.maxWidth}>
            <EditContext.Provider value={context}>
                <Grid fluid>
                    <Row>
                        <Col sm={10}>
                            {/* <Form context={context}>{children}</Form> */}
                            {/* <EditForm>{children}</EditForm> */}
                        </Col>
                        <Col sm={2}>
                            <EditSidebar />
                        </Col>
                    </Row>
                </Grid>
            </EditContext.Provider>
        </QueryState>
    </Page>
);
