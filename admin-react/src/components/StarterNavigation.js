import React from "react";
import {
    SideNavigation,
    NavigationContent,
    NavigationHeader,
    Header,
    LinkItem,
    Section
} from "@atlaskit/side-navigation";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import MediaServicesDocumentIcon from "@atlaskit/icon/glyph/media-services/document";
import logo from "../assets/logo.svg";
import styled from "styled-components";
import { matchPath } from "react-router";
import { useLocation } from "react-router-dom";

// TODO: make matchPath work. You need to convert this to function component to use the useLocation hook. Just convert everything to function components.
// TODO: you dont need redux until you handle the login JWT. Just use hooks. You don't need saga either.
// TODO: make the object of page links (below) be relocated to a separate file (a 'data' folder?) and then used in: this, react router (map over it), and the eventual breadcrumb bar.
// TODO: use a normal dropdown (not split button) on the right side of the data table which will be labelled 'Action'. Use atlaskit's table component instead of devextreme.
// TODO: make the 'create new' and 'edit' pages use the same page component, just with different state so that the 'new' page doesnt show the meta tab or any tab bar at all.

export default function SidebarNavigation(props) {
    const state = {
        nav: {
            home: [["Readme", "/", MediaServicesDocumentIcon]],
            system: [["Users", "/users", PeopleIcon]]
        }
    };

    const HeaderLink = ({ children, ...props }) => {
        return (
            <LinkItem href="/" {...props}>
                {children}
            </LinkItem>
        );
    };

    const mode = process.env.REACT_APP_PROJECT_MODE;

    const modeIndicator = mode === "production" ? "" : ` (${mode})`;

    const location = useLocation();

    return (
        <SideNavigation label="Main Navigation">
            <NavigationContent>
                <NavigationHeader>
                    <Header
                        component={HeaderLink}
                        iconBefore={
                            <AppLogo
                                src={logo}
                                alt={`${process.env.REACT_APP_PROJECT_NAME} logo`}
                            />
                        }
                        description={process.env.REACT_APP_PROJECT_NAME}
                    >
                        {process.env.REACT_APP_PROJECT_ABBREV + modeIndicator}
                    </Header>
                </NavigationHeader>
                {Object.entries(state.nav).map(([section, links], i) => {
                    return (
                        <Section key={`{i}-${section}`} title={section}>
                            {links.map(([title, url, Icon], i) => {
                                return (
                                    <LinkItem
                                        key={`${i}-${title}`}
                                        iconBefore={
                                            <Icon label={title} size="medium" />
                                        }
                                        href={url}
                                        isSelected={matchPath(
                                            location.pathname,
                                            {
                                                path: url,
                                                exact: true
                                            }
                                        )}
                                    >
                                        {title}
                                    </LinkItem>
                                );
                            })}
                        </Section>
                    );
                })}
            </NavigationContent>
        </SideNavigation>
    );
}

const AppLogo = styled.img`
    width: 100%;
`;
