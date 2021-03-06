import PropTypes from "prop-types";
import React, { Component } from "react";
import { Router, Route } from "react-router";
import createBrowserHistory from "history/createBrowserHistory";
import App from "./App";
import HomePage from "../pages/HomePage";
import UsersPage from "../pages/UsersPage";

export default class MainRouter extends Component {
    constructor() {
        super();
        this.state = {
            navOpenState: {
                isOpen: true,
                width: 304
            }
        };
    }

    getChildContext() {
        return {
            navOpenState: this.state.navOpenState
        };
    }

    appWithPersistentNav = () => props => (
        <App onNavResize={this.onNavResize} {...props} />
    );

    onNavResize = navOpenState => {
        this.setState({
            navOpenState
        });
    };

    render() {
        return (
            <Router history={createBrowserHistory()}>
                <Route component={this.appWithPersistentNav()}>
                    <Route path="/" component={HomePage} />
                    <Route path="/users" component={UsersPage} />
                </Route>
            </Router>
        );
    }
}

MainRouter.childContextTypes = {
    navOpenState: PropTypes.object
};
