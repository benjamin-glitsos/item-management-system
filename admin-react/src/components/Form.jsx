import { useContext, useEffect } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { UsersEditContext } from "%/pages/UsersEdit";
import nullToEmptyStr from "%/utilities/nullToEmptyStr";

export default ({ context, children }) => {
    const cx = useContext(context);

    const setFormValues = () => {
        for (const [key, value] of Object.entries(cx.usersData)) {
            cx.form.setValue(key, nullToEmptyStr(value));
        }
    };

    useEffect(setFormValues, []);

    return (
        <form onSubmit={cx.form.handleSubmit(cx.mutation.mutate)}>
            <Grid fluid>
                <Row>{children}</Row>
                <Row end="xs">
                    <Col sm={12}>
                        <ButtonGroupStyles>
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
