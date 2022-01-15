import { Fragment } from "react";
import { useLocation } from "react-router-dom";
import {
    SideNavigation,
    NavigationContent,
    NavigationHeader,
    Header,
    LinkItem,
    Section
} from "@atlaskit/side-navigation";
import styled from "styled-components";
import HomeCircleIcon from "@atlaskit/icon/glyph/home-circle";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import DocumentsIcon from "@atlaskit/icon/glyph/documents";
import doesMatchRouterLocation from "%/utilities/doesMatchRouterLocation";
import makeImagePath from "%/utilities/makeImagePath";

export default () => {
    const location = useLocation();
    const projectMode =
        process.env.REACT_APP_PROJECT_MODE || process.env.NODE_ENV;
    const projectName =
        process.env.REACT_APP_PROJECT_NAME || "Item Management System";
    const projectAbbrev = process.env.REACT_APP_PROJECT_ABBREV || "IMS";

    return (
        <SidebarStyles>
            <SideNavigation>
                <NavigationContent>
                    <ContentStyles>
                        <NavigationHeader>
                            <Header
                                component={({ children, ...props }) => (
                                    <LinkItem href="/" {...props}>
                                        {children}
                                    </LinkItem>
                                )}
                                iconBefore={
                                    <AppLogo
                                        src={
                                            process.env.PUBLIC_URL +
                                            "/images/logo.svg"
                                        }
                                        alt={`${projectName} logo`}
                                    />
                                }
                                description={
                                    <Fragment>
                                        {projectName}
                                        {projectMode === "production" ? null : (
                                            <Fragment>
                                                <br />
                                                {`(${projectMode})`}
                                            </Fragment>
                                        )}
                                    </Fragment>
                                }
                            >
                                {projectAbbrev}
                            </Header>
                        </NavigationHeader>
                        <Section key="SideNavigation/Section/Home" title="Home">
                            <LinkItem
                                key="SideNavigation/LinkItem/Readme"
                                iconBefore={<HomeCircleIcon size="medium" />}
                                href="/"
                                isSelected={doesMatchRouterLocation(
                                    "/",
                                    location
                                )}
                            >
                                Readme
                            </LinkItem>
                        </Section>
                        <Section
                            key="SideNavigation/Section/Content"
                            title="Content"
                        >
                            <LinkItem
                                key="SideNavigation/LinkItem/Items"
                                iconBefore={<DocumentsIcon size="medium" />}
                                href="/items"
                                isSelected={doesMatchRouterLocation(
                                    "/items",
                                    location
                                )}
                            >
                                Items
                            </LinkItem>
                        </Section>
                        <Section
                            key="SideNavigation/Section/System"
                            title="System"
                        >
                            <LinkItem
                                key="SideNavigation/LinkItem/Users"
                                iconBefore={<PeopleIcon size="medium" />}
                                href="/users"
                                isSelected={doesMatchRouterLocation(
                                    "/users",
                                    location
                                )}
                            >
                                Users
                            </LinkItem>
                        </Section>
                    </ContentStyles>
                </NavigationContent>
            </SideNavigation>
        </SidebarStyles>
    );
};

const AppLogo = styled.img`
    width: 100%;
`;

const ContentStyles = styled.div`
    position: fixed;
    top: 0;
`;

const SidebarStyles = styled.div`
    overflow-y: auto;
`;
