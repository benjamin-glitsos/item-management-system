import PropTypes from "prop-types";
import React, { Component } from "react";
import MainSection from "../components/MainSection";
import ContentWrapper from "../components/ContentWrapper";
import PageTitle from "../components/PageTitle";

export default class UsersPage extends Component {
    static contextTypes = {
        showModal: PropTypes.func,
        addFlag: PropTypes.func,
        onConfirm: PropTypes.func,
        onCancel: PropTypes.func,
        onClose: PropTypes.func
    };

    render() {
        return (
            <ContentWrapper>
                <PageTitle>Users</PageTitle>
                <MainSection />
            </ContentWrapper>
        );
    }
}
