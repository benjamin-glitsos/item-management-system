import { useContext } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { UsersEditContext } from "%/routes/UsersEdit";

export default ({ children }) => {
    const context = useContext(UsersEditContext);
    return (
        <form onSubmit={context.form.handleSubmit(x => console.log(x))}>
            <Grid fluid>
                <Row>{children}</Row>
                <Row end="xs">
                    <Col sm={12}>
                        <ButtonGroupStyles>
                            <ButtonGroup>
                                <Button
                                    appearance="subtle"
                                    onClick={context.page.handleReturn}
                                >
                                    Cancel
                                </Button>
                                <Button type="submit" appearance="primary">
                                    Submit
                                </Button>
                            </ButtonGroup>
                        </ButtonGroupStyles>
                    </Col>
                </Row>
            </Grid>
        </form>
    );
};

const ButtonGroupStyles = styled.div`
    margin-top: 24px;
`;
