import { cloneElement, useEffect } from "react";
import R from "ramda";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import nullToEmptyStr from "utilities/nullToEmptyStr";
import formHandler from "utilities/formHandler";

export default ({ context, children }) => {
    const form = useForm({
        resolver: useYupSchemaResolver({
            schema: context.schema,
            originalData: context.data
        })
    });

    useEffect(() => {
        for (const [key, value] of Object.entries(context.data)) {
            form.setValue(key, nullToEmptyStr(value));
        }
    }, []);

    const isReady = !context.schema?.properties;

    return (
        <form onSubmit={form.handleSubmit(formHandler)}>
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
                                    isDisabled={isReady}
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
