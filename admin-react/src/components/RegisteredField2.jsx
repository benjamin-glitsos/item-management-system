import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";

export default ({
    name,
    title,
    Component,
    columnWidths,
    isCreate,
    schemaProperties,
    errors,
    register,
    ...props
}) => {
    return (
        <Col {...columnWidths}>
            <Field
                name={name}
                title={title}
                Component={Component}
                isCreate={isCreate}
                schemaProperties={schemaProperties}
                errors={errors}
                additionalProps={register(name)}
                {...props}
            />
        </Col>
    );
};
