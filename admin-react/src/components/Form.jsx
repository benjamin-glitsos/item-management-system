import { useContext, useEffect } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { UsersEditContext } from "%/routes/UsersEdit";
import nullToEmptyStr from "%/utilities/nullToEmptyStr";

export default ({ children }) => {
    const context = useContext(UsersEditContext);

    const setFormValues = () => {
        for (const [key, value] of Object.entries(context.usersData)) {
            context.form.setValue(key, nullToEmptyStr(value));
        }
    };

    useEffect(setFormValues, []);

    return (
        <form onSubmit={context.form.handleSubmit(context.mutate)}>
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
