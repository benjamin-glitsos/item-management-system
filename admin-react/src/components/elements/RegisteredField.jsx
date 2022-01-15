import { Col } from "react-flexbox-grid";
import Field from "elements/Field";

export default ({
    key,
    title,
    Component,
    columnWidths,
    isDisabled,
    ...props
}) => (
    <Col {...columnWidths}>
        <Field
            key={key}
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
