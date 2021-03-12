import { Fragment } from "react";
import doesMatchLocation from "../utilities/doesMatchLocation";
import {
    SideNavigation,
    NavigationContent,
    NavigationHeader,
    Header,
    LinkItem,
    Section
} from "@atlaskit/side-navigation";
import logo from "../assets/logo.svg";
import styled from "styled-components";
import MediaServicesDocumentIcon from "@atlaskit/icon/glyph/media-services/document";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import UserAvatarCircleIcon from "@atlaskit/icon/glyph/user-avatar-circle";

export default () => {
    const projectMode =
        process.env.REACT_APP_PROJECT_MODE || process.env.NODE_ENV;
    const projectName =
        process.env.REACT_APP_PROJECT_NAME || "Item Management System";
    const projectAbbrev = process.env.REACT_APP_PROJECT_ABBREV || "IMS";

    return (
        <SideNavigation label="Main Navigation">
            <NavigationContent>
                <NavigationHeader>
                    <Header
                        component={({ children, ...props }) => (
                            <LinkItem href="/" {...props}>
                                {children}
                            </LinkItem>
                        )}
                        iconBefore={
                            <AppLogo src={logo} alt={`${projectName} logo`} />
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
                        iconBefore={<MediaServicesDocumentIcon size="medium" />}
                        href="/"
                        isSelected={doesMatchLocation("/")}
                    >
                        Readme
                    </LinkItem>
                </Section>
                <Section key="SideNavigation/Section/System" title="System">
                    <LinkItem
                        key="SideNavigation/LinkItem/Users"
                        iconBefore={<PeopleIcon size="medium" />}
                        href="/users"
                        isSelected={doesMatchLocation("/users")}
                    >
                        Users
                    </LinkItem>
                </Section>
            </NavigationContent>
        </SideNavigation>
    );
};

const AppLogo = styled.img`
    width: 100%;
`;
