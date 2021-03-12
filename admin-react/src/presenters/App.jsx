import styled from "styled-components";
import composeHooks from "react-hooks-compose";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import Location from "%/containers/Location";
import SidebarNavigation from "%/presenters/SidebarNavigation";

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
