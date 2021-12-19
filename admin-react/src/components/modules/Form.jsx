import { cloneElement } from "react";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import nullToEmptyStr from "utilities/nullToEmptyStr";

export default ({ context, children }) => {
    const form = useForm({
        resolver: useYupSchemaResolver({
            schema: context.schema,
            originalData: context.data
        })
    });

    for (const [key, value] of Object.entries(context.data)) {
        form.setValue(key, nullToEmptyStr(value));
    }

    return (
        <form onSubmit={form.handleSubmit(x => console.log(x))}>
            <Grid fluid>
                <Row>
                    {cloneElement(children, { context: { ...context, form } })}
                </Row>
                <Row end="xs">
                    <Col sm={12}>
                        <Buttons>
                            <ButtonGroup>
                                <Button
                                    appearance="subtle"
                                    onClick={context.page.handleReturn}
                                >
                                    Cancel
                                </Button>
                                <Button
                                    type="submit"
                                    appearance="primary"
                                    isDisabled={!context.schema?.properties}
                                >
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
