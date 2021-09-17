import { useContext } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { UsersEditContext } from "%/routes/UsersEdit";

export default ({ children }) => {
    const context = useContext(UsersEditContext);
    return (
        <form
            onSubmit={context.form.handleSubmit(x => console.log(context.form))}
        >
            <Grid fluid>
                <Row>{children}</Row>
                <Row end="xs">
                    <Col sm={12}>
                        <Buttons>
                            <Button
                                appearance="subtle"
                                onClick={context.page.handleReturn}
                            >
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
};

const Buttons = styled(ButtonGroup)`
    margin-top: 24px;
`;
