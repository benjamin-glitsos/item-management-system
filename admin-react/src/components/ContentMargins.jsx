import React from "react";
import { Grid, GridColumn } from "@atlaskit/page";
import { gridSize } from "@atlaskit/theme";
import cond from 'switch-function'
import styled from "styled-components";

export default ({ type, children }) => (
    <Margins type={type}>{children}</Margins>
);

const Margins = styled.div`
    ${cond(props => props.type, {
        full_width: "margin: 0 3em;",
        default: ""
    })}
`;
