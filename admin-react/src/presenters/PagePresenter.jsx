import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "%/presenters/SidebarNavigation";
import { FlagsProvider } from "@atlaskit/flag";

export default ({ children }) => (
    <Page
        navigation={
            <SidebarStyles>
                <SidebarNavigation />
            </SidebarStyles>
        }
    >
        <FlagsProvider>
            <PageStyles>{children}</PageStyles>
        </FlagsProvider>
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
