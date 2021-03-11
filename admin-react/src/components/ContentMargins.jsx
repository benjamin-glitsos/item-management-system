import React from "react";
import { Grid, GridColumn } from "@atlaskit/page";
import { gridSize } from "@atlaskit/theme";
import cond from 'switch-function'
import styled from "styled-components";

export default ({ type, children }) => {
    const maxWidth = cond(type, {
        wide: 1600,
        medium: 1080,
        narrow: 800
    })

    return <Margins maxWidth>{children}</Margins>
}

const Margins = styled.div`
    margin: 0 auto;
    max-width: ${props => maxWidth}px;
    padding: 0 3em;
`;
