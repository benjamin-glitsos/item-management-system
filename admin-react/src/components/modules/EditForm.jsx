import { useState, useContext, cloneElement } from "react";
import R from "ramda";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import Button, { ButtonGroup, LoadingButton } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import { EditContext } from "templates/EditTemplate";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import nullToEmptyStr from "utilities/nullToEmptyStr";
import noNewDataToSubmitToast from "utilities/noNewDataToSubmitToast";
import successToast from "utilities/successToast";
import unspecifiedErrorToast from "utilities/unspecifiedErrorToast";
import simplur from "simplur";

export default ({ children }) => {
    const context = useContext(EditContext);

    const [isSubmitting, setIsSubmitting] = useState(false);

    const form = useForm({
        resolver: useYupSchemaResolver({
            schema: context.schemaData,
            originalData: context.itemData,
            setIsQueryEnabled: context.setIsQueryEnabled
        })
    });

    for (const [key, value] of Object.entries(context.itemData)) {
        form.setValue(key, nullToEmptyStr(value));
    }

    const isReady = !context.schemaData?.properties;

    const handler = form.handleSubmit(data => {
        if (R.isEmpty(data)) {
            noNewDataToSubmitToast();
        } else {
            setIsSubmitting(true);
            context.mutation.mutate(data, {
                onSettled: () => setIsSubmitting(false),
                onError: () => unspecifiedErrorToast(),
                onSuccess: async () => {
                    await context.queries[1].refetch();
                    const numberOfChanges = Object.keys(data).length;
                    successToast({
                        title: simplur`Changed ${numberOfChanges} field[|s]`,
                        description: "Changes were submitted."
                    });
                }
            });
        }
    });

    console.log(form);

    return (
        <form onSubmit={handler}>
            {cloneElement(children, {
                context: { ...context, form, isDisabled: isSubmitting }
            })}
            <Grid fluid>
                <Row></Row>
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
                                    isDisabled={isReady || isSubmitting}
                                    isLoading={isSubmitting}
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
