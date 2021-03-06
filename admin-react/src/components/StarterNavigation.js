import PropTypes from "prop-types";
import React, { Component } from "react";
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

// TODO: you dont need redux until you handle the login JWT. Just use hooks. You don't need saga either.
// TODO: make the object of page links (below) be relocated to a separate file (a 'data' folder?) and then used in: this, react router (map over it), and the eventual breadcrumb bar.
// TODO: use a normal dropdown (not split button) on the right side of the data table which will be labelled 'Action'. Use atlaskit's table component instead of devextreme.

const HeaderLink = ({ children, ...props }) => {
    return (
        <LinkItem href="/" {...props}>
            {children}
        </LinkItem>
    );
};

const modeIndicator = (() => {
    const mode = process.env.REACT_APP_PROJECT_MODE;
    if (mode === "production") {
        return "";
    } else {
        return ` (${mode})`;
    }
})();

export default class StarterNavigation extends Component {
    state = {
        nav: {
            home: [["Readme", "/", MediaServicesDocumentIcon]],
            system: [["Users", "/users", PeopleIcon]]
        }
    };

    static contextTypes = {
        navOpenState: PropTypes.object,
        router: PropTypes.object
    };

    shouldComponentUpdate(nextProps, nextContext) {
        return true;
    }

    render() {
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
                            {process.env.REACT_APP_PROJECT_ABBREV +
                                modeIndicator}
                        </Header>
                    </NavigationHeader>
                    {Object.entries(this.state.nav).map(
                        ([section, links], i) => {
                            return (
                                <Section key={`{i}-${section}`} title={section}>
                                    {links.map(([title, url, Icon], i) => {
                                        return (
                                            <LinkItem
                                                key={`${i}-${title}`}
                                                iconBefore={
                                                    <Icon
                                                        label={title}
                                                        size="medium"
                                                    />
                                                }
                                                isSelected={this.context.router.isActive(
                                                    url,
                                                    true
                                                )}
                                                href={url}
                                            >
                                                {title}
                                            </LinkItem>
                                        );
                                    })}
                                </Section>
                            );
                        }
                    )}
                </NavigationContent>
            </SideNavigation>
        );
    }
}

const AppLogo = styled.img`
    width: 100%;
`;
