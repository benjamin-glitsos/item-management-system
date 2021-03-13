import { Fragment } from "react";
import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "%/components/SidebarNavigation";

export default ({ children }) => (
    <Fragment>
        <FullHeight>
            <Page navigation={<SidebarNavigation />}>{children}</Page>
        </FullHeight>
    </Fragment>
);

const FullHeight = styled.div`
    height: 100vh;
`;
