import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "@/presenters/SidebarNavigation";
import Location from "@/containers/Location";
import styled from "styled-components";
import composeHooks from "react-hooks-compose";

export default ({ children }) => (
    <FullHeight>
        <Page navigation={<SidebarNavigation />}>{children}</Page>
    </FullHeight>
);

const FullHeight = styled.div`
    height: 100vh;
`;
