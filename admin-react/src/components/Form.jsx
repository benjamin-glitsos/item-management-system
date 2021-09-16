import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";

export default ({ page, open, edit, handler, form, children }) => (
    <form onSubmit={handler}>
        <Grid fluid>
            <Row>{children}</Row>
            <Row end="xs">
                <Col sm={12}>
                    <Buttons>
                        <Button appearance="subtle" onClick={page.handleReturn}>
                            Cancel
                        </Button>
                        <Button type="submit" appearance="primary">
                            Submit
                        </Button>
                    </Buttons>
                </Col>
            </Row>
        </Grid>
    </form>
);

const Buttons = styled(ButtonGroup)`
    margin-top: 24px;
`;
