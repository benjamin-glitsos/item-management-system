import { useContext } from "react";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";

export default ({
    name,
    title,
    Component,
    columnWidths,
    context,
    ...props
}) => {
    const cx = useContext(context);
    return (
        <Col {...columnWidths}>
            <Field
                name={name}
                title={title}
                Component={Component}
                isCreate={!!cx?.create}
                schemaProperties={cx.schemaData.properties}
                errors={cx.form.formState?.errors}
                additionalProps={cx.form.register(name)}
                {...props}
            />
        </Col>
    );
};
