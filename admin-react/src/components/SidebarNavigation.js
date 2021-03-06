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
