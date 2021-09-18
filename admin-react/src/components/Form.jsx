import { useContext, useEffect } from "react";
import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { UsersEditContext } from "%/pages/UsersEdit";
import setFormValues from "%/utilities/setFormValues";

export default ({ context, children }) => {
    const cx = useContext(context);

    useEffect(() => setFormValues(cx.form.setValue, cx.entityData), []);

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
