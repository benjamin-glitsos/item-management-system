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
import DashboardIcon from "@atlaskit/icon/glyph/dashboard";
import logo from "../images/logo.svg";
import styled from "styled-components";

export default class StarterNavigation extends Component {
    state = {
        nav: {
            home: [["Dashboard", "/", DashboardIcon]],
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
        console.log(process.env);
        return (
            <SideNavigation label="Main Navigation">
                <NavigationContent>
                    <NavigationHeader>
                        <LinkItem href="/">
                            <Header
                                iconBefore={
                                    <AppLogo
                                        src={logo}
                                        alt={`${process.env.REACT_APP_PROJECT_NAME} logo`}
                                    />
                                }
                                description={process.env.REACT_APP_PROJECT_NAME}
                            >
                                {process.env.REACT_APP_PROJECT_ABBREV}
                            </Header>
                        </LinkItem>
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
