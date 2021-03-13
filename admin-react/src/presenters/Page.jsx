import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import { ToastContainer } from "react-toastify";
import SidebarNavigation from "%/components/SidebarNavigation";

export default ({ children }) => (
    <FullHeight>
        <Page navigation={<SidebarNavigation />}>{children}</Page>
    </FullHeight>
);
// <ToastContainer>{children}</ToastContainer>

const FullHeight = styled.div`
    height: 100vh;
`;
