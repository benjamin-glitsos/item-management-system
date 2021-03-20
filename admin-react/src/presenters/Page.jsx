import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "%/components/SidebarNavigation";

export default ({ children }) => (
    <Page
        navigation={
            <SidebarStyles>
                <SidebarNavigation />
            </SidebarStyles>
        }
    >
        <PageStyles>{children}</PageStyles>
    </Page>
);

const SidebarStyles = styled.div`
    height: 100vh;
    overflow-y: auto;
    position: fixed;
`;

const PageStyles = styled.div`
    margin-left: 240px;
    min-height: calc(100vh - 49px);
    padding-bottom: 15px;
`;
