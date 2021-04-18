import styled from "styled-components";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import ContentMargins from "%/components/ContentMargins";

export default ({ title, breadcrumbs, maxWidth, children }) => {
    const sidePadding = 30;
    return (
        <MarginBottom sidePadding={sidePadding}>
            <ContentMargins maxWidth={maxWidth - sidePadding * 2}>
                <PageHeader
                    breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
                >
                    {title}
                </PageHeader>
                {children}
            </ContentMargins>
        </MarginBottom>
    );
};

const MarginBottom = styled.div`
    margin-bottom: 28px;
    ${props => {
        const sidePadding = props.sidePadding;
        return `
            padding-left: ${sidePadding};
            padding-right: ${sidePadding};
            `;
    }}
`;
