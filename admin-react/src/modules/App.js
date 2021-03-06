import PropTypes from "prop-types";
import React, { Component, Fragment } from "react";
import Flag, { FlagGroup } from "@atlaskit/flag";
import Modal from "@atlaskit/modal-dialog";
import Page from "@atlaskit/page";
import "@atlaskit/css-reset";
import SidebarNavigation from "../components/SidebarNavigation";
import styled from "styled-components";

export default ({ children }) => (
    <PageWrapper>
        <Page navigation={<SidebarNavigation />}>{children}</Page>
    </PageWrapper>
);

const PageWrapper = styled.div`
    height: 100vh;
`;
