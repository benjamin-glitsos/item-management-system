import { Col } from "react-flexbox-grid";
import Field from "elements/Field";

export default ({ key, title, Component, columnWidths, ...props }) => (
    <Col {...columnWidths}>
        <Field
            key={key}
            title={title}
            Component={Component}
            schemaProperties={context?.schema?.properties}
            errors={context.form.formState.errors}
            additionalProps={context.form.register(name)}
            {...props}
        />
    </Col>
);
