import { createContext } from "react";
import styled from "styled-components";
import { Grid, Row, Col } from "react-flexbox-grid";
import Page from "modules/Page";
import EditSidebar from "modules/EditSidebar";
import EditForm from "modules/EditForm";
import QueryState from "modules/QueryState";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";

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
                        <Col sm={12}>
                            <PageHeaderStyles>
                                <PageHeader
                                    breadcrumbs={
                                        <BreadcrumbBar breadcrumbs={[]} />
                                    }
                                >
                                    {context.page.tabTitle}
                                </PageHeader>
                            </PageHeaderStyles>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={10}>
                            <EditForm>{children}</EditForm>
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

const PageHeaderStyles = styled.div`
    margin-left: 16px;
`;
