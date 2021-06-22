import ContentLayout from "%/components/ContentLayout";
import styled from "styled-components";

export default ({ title, breadcrumbs, isLoading, children }) => (
    <Styles>
        <ContentLayout
            title={!isLoading ? title : ""}
            breadcrumbs={!isLoading ? breadcrumbs : []}
            maxWidth={"1200px"}
        >
            {children}
        </ContentLayout>
    </Styles>
);

const Styles = styled.div`
    h1 {
        margin-bottom: -1em;
    }
`;
