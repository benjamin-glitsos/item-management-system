import { cloneElement } from "react";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import Button, { ButtonGroup } from "@atlaskit/button";
import { Grid, Row, Col } from "react-flexbox-grid";
import useYupSchemaResolver from "hooks/useYupSchemaResolver";
import nullToEmptyStr from "utilities/nullToEmptyStr";

export default ({ context, children }) => {
    const form = useForm({
        resolver: useYupSchemaResolver(context.schema)
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

// const schema = {
//     $schema: "http://json-schema.org/draft-07/schema#",
//     type: "object",
//     properties: {
//         name: {
//             description: "Name of the person",
//             type: "string"
//         },
//         email: {
//             type: "string",
//             format: "email",
//             emailDescription: "lalalala",
//             maxLength: 50,
//             minLength: 1
//         },
//         fooorbar: {
//             type: "string",
//             matches: "(foo|bar)"
//         },
//         age: {
//             description: "Age of person",
//             type: "number",
//             exclusiveMinimum: 0,
//             required: true
//         },
//         characterType: {
//             enum: ["good", "bad"],
//             enum_titles: ["Good", "Bad"],
//             type: "string",
//             title: "Type of people",
//             propertyOrder: 3
//         },
//         additionalNotes: {
//             maxLength: 1048576,
//             type: "string"
//         }
//     },
//     required: ["name", "email"]
// };
//
// const form = useForm({
//     resolver: useYupSchemaResolver(schema)
//     // queries[0]
// });
//
