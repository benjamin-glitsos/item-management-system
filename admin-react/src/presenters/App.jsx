import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "@/presenters/SidebarNavigation";
import styled from "styled-components";
import Location from "@/containers/Location";
import composeHooks from "react-hooks-compose";

const SidebarNavigationComposition = composeHooks({ Location })(
    SidebarNavigation
);

export default ({ children }) => (
    <FullHeight>
        <Page navigation={<SidebarNavigationComposition />}>{children}</Page>
    </FullHeight>
);

const FullHeight = styled.div`
    height: 100vh;
`;
