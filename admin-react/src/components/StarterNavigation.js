import PropTypes from "prop-types";
import React, { Component } from "react";
import { Link } from "react-router";
import {
    SideNavigation,
    NavigationContent,
    ButtonItem,
    NavigationHeader,
    Header
} from "@atlaskit/side-navigation";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import DashboardIcon from "@atlaskit/icon/glyph/dashboard";
import AtlassianIcon from "@atlaskit/icon/glyph/atlassian";
import ArrowleftIcon from "@atlaskit/icon/glyph/arrow-left";

import HelpDropdownMenu from "../components/HelpDropdownMenu";
import AccountDropdownMenu from "../components/AccountDropdownMenu";
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
        const backIcon = <ArrowleftIcon label="Back icon" size="medium" />;

        return (
            <SideNavigation label="Main Navigation">
                <NavigationContent>
                    <NavigationHeader>
                        <Header
                            iconBefore={<img src={logo} />}
                            description={process.env.PROJECT_NAME}
                        >
                            {process.env.PROJECT_ABBREV}
                        </Header>
                    </NavigationHeader>
                    {this.state.navLinks.map(link => {
                        const [url, title, Icon] = link;
                        return (
                            <ButtonItem
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
