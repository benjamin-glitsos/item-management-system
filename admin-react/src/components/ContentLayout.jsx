import styled from "styled-components";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import ContentMargins from "%/components/ContentMargins";

export default ({ title, breadcrumbs, maxWidth, children }) => {
    return (
        <Content maxWidth={maxWidth}>
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            >
                {title}
            </PageHeader>
            {children}
        </Content>
    );
};

const Content = styled(ContentMargins)`
    margin-bottom: 28px;
`;
