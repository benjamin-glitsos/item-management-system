import styled from "styled-components";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "%/components/SidebarNavigation";
import { FlagsProvider } from "@atlaskit/flag";

export default ({ children }) => (
    <PageStyles>
        <Page
            navigation={
                <SidebarStyles>
                    <SidebarNavigation />
                </SidebarStyles>
            }
        >
            <FlagsProvider>
                <ContentStyles>{children}</ContentStyles>
            </FlagsProvider>
        </Page>
    </PageStyles>
);

const SidebarStyles = styled.div`
    overflow-y: auto;
    max-height: 100vh;
`;

const ContentStyles = styled.div`
    background-color: white;
    min-height: calc(100vh - 49px);
    padding-bottom: 15px;
    padding-top: 3em;
    margin-top: -3em;
`;

const PageStyles = styled.div`
    background-color: #fafbfc;
    div > div > div:nth-child(2) {
        z-index: 3;
    }
`;
