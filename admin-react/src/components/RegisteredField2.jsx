import { useContext } from "react";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";
import { UsersEditContext } from "%/routes/UsersEdit";

export default ({ name, title, Component, columnWidths, ...props }) => {
    const context = useContext(UsersEditContext);
    return (
        <Col {...columnWidths}>
            <Field
                name={name}
                title={title}
                Component={Component}
                isCreate={context?.create}
                schemaProperties={{}}
                errors={[]}
                additionalProps={context.form.register(name)}
                {...props}
            />
        </Col>
    );
};
