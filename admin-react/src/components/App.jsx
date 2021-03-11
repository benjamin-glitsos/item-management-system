import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "../components/SidebarNavigation";
import styled from "styled-components";

export default ({ children }) => (
    <FullHeight>
        <Page navigation={<SidebarNavigation />}>{children}</Page>
    </FullHeight>
);

const FullHeight = styled.div`
    height: 100vh;
`;
