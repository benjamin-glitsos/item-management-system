import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";

export default ({ page, open, edit, form, children }) => (
    <form onSubmit={form.handleSubmit(x => console.log(x))}>
        <Grid fluid>
            <Row>{children}</Row>
            <Row end="xs">
                <Col sm={12}>
                    <Buttons>
                        <ButtonGroup>
                            <Button
                                appearance="subtle"
                                onClick={page.handleReturn}
                            >
                                Cancel
                            </Button>
                            <Button type="submit" appearance="primary">
                                Submit
                            </Button>
                        </ButtonGroup>
                    </Buttons>
                </Col>
            </Row>
        </Grid>
    </form>
);

const Buttons = styled.div`
    margin-top: 24px;
`;
