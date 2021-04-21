import { useContext } from "react";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";
import { OpenContext } from "%/components/Open/Open";

export default ({ name, title, sm, Component, ...props }) => {
    const context = useContext(OpenContext);
    return (
        <Col sm={sm}>
            <Field
                name={name}
                title={title}
                Component={Component}
                action={context.action}
                schemaProperties={context.state.schema.properties}
                errors={context.errors}
                additionalProps={context.register(name)}
                {...props}
            />
        </Col>
    );
};
