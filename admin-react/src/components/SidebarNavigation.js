import React from "react";
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
import { matchPath } from "react-router";
import { useLocation } from "react-router-dom";
import MediaServicesDocumentIcon from "@atlaskit/icon/glyph/media-services/document";
import PeopleIcon from "@atlaskit/icon/glyph/people";

// TODO: make matchPath work. You need to convert this to function component to use the useLocation hook. Just convert everything to function components.
// TODO: you dont need redux until you handle the login JWT. Just use hooks. You don't need saga either.
// TODO: make the object of page links (below) be relocated to a separate file (a 'data' folder?) and then used in: this, react router (map over it), and the eventual breadcrumb bar.
// TODO: use a normal dropdown (not split button) on the right side of the data table which will be labelled 'Action'. Use atlaskit's table component instead of devextreme.
// TODO: make the 'create new' and 'edit' pages use the same page component, just with different state so that the 'new' page doesnt show the meta tab or any tab bar at all.

export default () => {
    const pagesData = {
        home: [["Readme", "/", MediaServicesDocumentIcon]],
        system: [["Users", "/users", PeopleIcon]]
    };

    const HeaderLink = ({ children, ...props }) => {
        return (
            <LinkItem href="/" {...props}>
                {children}
            </LinkItem>
        );
    };

    const mode = process.env.REACT_APP_PROJECT_MODE || process.env.NODE_ENV;

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
                                alt={`${
                                    process.env.REACT_APP_PROJECT_NAME ||
                                    "Item Management System"
                                } logo`}
                            />
                        }
                        description={
                            process.env.REACT_APP_PROJECT_NAME ||
                            "Item Management System"
                        }
                    >
                        {(process.env.REACT_APP_PROJECT_ABBREV || "IMS") +
                            modeIndicator}
                    </Header>
                </NavigationHeader>
                {Object.entries(pagesData).map(([section, links], i) => {
                    return (
                        <Section
                            key={`${i}-${section}-navigation`}
                            title={section}
                        >
                            {links.map(([title, path, Icon, Page], i) => {
                                return (
                                    <LinkItem
                                        key={`${i}-${title}`}
                                        iconBefore={
                                            <Icon label={title} size="medium" />
                                        }
                                        href={path}
                                        isSelected={matchPath(
                                            location.pathname,
                                            {
                                                path: path,
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
};

const AppLogo = styled.img`
    width: 100%;
`;
