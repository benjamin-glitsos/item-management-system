import { useContext } from "react";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";
import { Context } from "%/pages/UsersEdit";

export default ({ name, title, Component, columnWidths, ...props }) => {
    const context = useContext(Context);
    return (
        <Col {...columnWidths}>
            <Field
                name={name}
                title={title}
                Component={Component}
                isCreate={!!context?.create}
                schemaProperties={context.schemaData.properties}
                errors={context.form.formState?.errors}
                additionalProps={context.form.register(name)}
                {...props}
            />
        </Col>
    );
};
