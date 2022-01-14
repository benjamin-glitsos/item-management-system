import { Col } from "react-flexbox-grid";
import Field from "elements/Field";

export default ({
    name,
    title,
    Component,
    columnWidths,
    isDisabled,
    context,
    ...props
}) => (
    <Col {...columnWidths}>
        <Field
            name={name}
            title={title}
            Component={Component}
            isCreate={!!context?.create}
            schemaProperties={context?.schema?.properties}
            errors={context.form.formState.errors}
            isDisabled={isDisabled}
            additionalProps={context.form.register(name)}
            {...props}
        />
    </Col>
);
