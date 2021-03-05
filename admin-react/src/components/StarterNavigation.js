import PropTypes from "prop-types";
import React, { Component } from "react";
import {
    SideNavigation,
    NavigationContent,
    ButtonItem,
    NavigationHeader,
    Header
} from "@atlaskit/side-navigation";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import DashboardIcon from "@atlaskit/icon/glyph/dashboard";
import logo from "../images/logo.svg";

export default class StarterNavigation extends Component {
    state = {
        navLinks: [
            ["/", "Dashboard", DashboardIcon],
            ["/users", "Users", PeopleIcon]
        ]
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
                            iconBefore={
                                <img
                                    src={logo}
                                    alt={`${process.env.PROJECT_NAME} logo`}
                                />
                            }
                            description={process.env.PROJECT_NAME}
                        >
                            {process.env.PROJECT_ABBREV}
                        </Header>
                    </NavigationHeader>
                    {this.state.navLinks.map(([url, title, Icon], i) => {
                        return (
                            <ButtonItem
                                key={`${i}-${title}`}
                                iconBefore={
                                    <Icon label={title} size="medium" />
                                }
                                isSelected={this.context.router.isActive(
                                    url,
                                    true
                                )}
                            >
                                {title}
                            </ButtonItem>
                        );
                    }, this)}
                </NavigationContent>
            </SideNavigation>
        );
    }
}
