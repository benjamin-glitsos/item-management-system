import React from "react";
import { Grid, GridColumn } from "@atlaskit/page";
import { gridSize } from "@atlaskit/theme";

export default ({ children }) => (
    <Grid>
        <GridColumn>{children}</GridColumn>
    </Grid>
);
