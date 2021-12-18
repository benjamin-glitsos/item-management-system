import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigationComponent from "%/components/SidebarNavigation";

export default ({ children }) => (
    <Styles>
        <Page navigation={<SidebarNavigation />}>
            <Content>{children}</Content>
        </Page>
    </Styles>
);

const SidebarNavigation = styled(SidebarNavigationComponent)`
    overflow-y: auto;
    max-height: 100vh;
`;

const Content = styled.div`
    background-color: white;
    min-height: calc(100vh - 15px);
    padding-bottom: 15px;
    padding-top: 3em;
    margin-top: -3em;
`;

const Styles = styled.div`
    background-color: #fafbfc;
`;
