import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "../components/SidebarNavigation";
import styled from "styled-components";

export default ({ children }) => (
    <PageWrapper>
        <Page navigation={<SidebarNavigation />}>{children}</Page>
    </PageWrapper>
);

const PageWrapper = styled.div`
    height: 100vh;
`;
