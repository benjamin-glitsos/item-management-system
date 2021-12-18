import { useContext } from "react";
import { Col } from "react-flexbox-grid";
import Field from "elements/Field";
import { OpenContext } from "%/components/Open";

export default ({ name, title, Component, columnWidths, ...props }) => {
    const context = useContext(OpenContext);
    return (
        <Col {...columnWidths}>
            <Field
                name={name}
                title={title}
                Component={Component}
                isCreate={context.isCreate}
                schemaProperties={context.state.schema.properties}
                errors={context.errors}
                additionalProps={context.register(name)}
                {...props}
            />
        </Col>
    );
};
