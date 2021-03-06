import PropTypes from "prop-types";
import React, { Component } from "react";
import ContentWrapper from "../components/ContentWrapper";
import PageTitle from "../components/PageTitle";
import ReactMarkdown from "react-markdown";
import readme from "../assets/README.md";

export default class ReadmePage extends Component {
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
                <PageTitle>Readme</PageTitle>
                <ReactMarkdown>{readme}</ReactMarkdown>
            </ContentWrapper>
        );
    }
}
