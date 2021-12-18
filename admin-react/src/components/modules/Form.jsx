import { useContext } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";

export default ({ context, children }) => {
    const cx = useContext(context);
    return (
        <form onSubmit={cx.form.handleSubmit(x => console.log(x))}>
            <Grid fluid>
                <Row>{children}</Row>
                <Row end="xs">
                    <Col sm={12}>
                        <Buttons>
                            <ButtonGroup>
                                <Button
                                    appearance="subtle"
                                    onClick={cx.page.handleReturn}
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
};

const Buttons = styled.div`
    margin-top: 24px;
`;
