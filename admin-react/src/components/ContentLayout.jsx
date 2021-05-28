import styled from "styled-components";
import { Grid, Row, Col } from "react-flexbox-grid";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import ContentMargins from "%/components/ContentMargins";

export default ({
    title,
    breadcrumbs,
    maxWidth,
    hasGrid,
    actions,
    children
}) => {
    const ContentPageHeader = () => (
        <PageHeader
            breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            actions={actions}
        >
            {title}
        </PageHeader>
    );
    return (
        <MarginBottom>
            <ContentMargins maxWidth={maxWidth}>
                {hasGrid ? (
                    <Grid fluid>
                        <Row>
                            <Col sm={10}>
                                <ContentPageHeader />
                            </Col>
                            <Col sm={2} />
                        </Row>
                    </Grid>
                ) : (
                    <ContentPageHeader />
                )}
                {children}
            </ContentMargins>
        </MarginBottom>
    );
};

const MarginBottom = styled.div`
    margin-bottom: 28px;
`;
