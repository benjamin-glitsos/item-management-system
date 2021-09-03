import ContentLayoutComponent from "%/components/ContentLayout";
import styled from "styled-components";

export default ({ title, breadcrumbs, isLoading, children }) => (
    <ContentLayout
        title={!isLoading ? title : ""}
        breadcrumbs={!isLoading ? breadcrumbs : []}
        maxWidth={"1200px"}
    >
        {children}
    </ContentLayout>
);

const ContentLayout = styled(ContentLayoutComponent)`
    h1 {
        margin-bottom: -1em;
    }
`;
