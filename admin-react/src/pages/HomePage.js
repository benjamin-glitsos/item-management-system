import PropTypes from "prop-types";
import React, { Component } from "react";
import ContentWrapper from "../components/ContentWrapper";
import PageTitle from "../components/PageTitle";
import ReactMarkdown from "react-markdown";
import readme from "!raw-loader!../assets/README.md";

export default class HomePage extends Component {
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
                <PageTitle>Home</PageTitle>
                <ReactMarkdown>{readme}</ReactMarkdown>
            </ContentWrapper>
        );
    }
}
