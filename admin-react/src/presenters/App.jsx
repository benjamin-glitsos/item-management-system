import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "../components/SidebarNavigation";
import Location from "../components/Location";
import styled from "styled-components";
import composeHooks from "react-hooks-compose";

export default ({ children }) => (
    <FullHeight>
        <Page navigation={composeHooks({ Location })(SidebarNavigation)}>
            {children}
        </Page>
    </FullHeight>
);

const FullHeight = styled.div`
    height: 100vh;
`;
