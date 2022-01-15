import { useState, useContext, cloneElement } from "react";
import R from "ramda";
import styled from "styled-components";
import Button, { ButtonGroup, LoadingButton } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { EditContext } from "templates/EditTemplate";
import noNewDataToSubmitToast from "utilities/noNewDataToSubmitToast";
import successToast from "utilities/successToast";
import unspecifiedErrorToast from "utilities/unspecifiedErrorToast";
import simplur from "simplur";
import cleanSubmitData from "utilities/cleanSubmitData";

export default ({ children }) => {
    const context = useContext(EditContext);

    const isReady = !context.schemaData?.properties;

    const handler = context.form.handleSubmit(data => {
        const submitData = cleanSubmitData(context.itemData, data);
        console.log(submitData);
        if (R.isEmpty(submitData)) {
            noNewDataToSubmitToast();
        } else {
            context.mutation.mutate(submitData, {
                onError: () => unspecifiedErrorToast(),
                onSuccess: async () => {
                    await context.queries[1].refetch();
                    const numberOfChanges = Object.keys(submitData).length;
                    successToast({
                        title: simplur`Changed ${numberOfChanges} field[|s]`,
                        description: "Changes were submitted."
                    });
                }
            });
        }
    });

    return (
        <form onSubmit={handler}>
            <Grid fluid>
                <Row>{children}</Row>
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
                                <LoadingButton
                                    type="submit"
                                    appearance="primary"
                                    isDisabled={isReady}
                                >
                                    Submit
                                </LoadingButton>
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
