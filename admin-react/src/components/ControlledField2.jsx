import { useContext } from "react";
import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";
import { UsersEditContext } from "%/routes/UsersEdit";

export default ({ name, title, Component, columnWidths, ...props }) => {
    const context = useContext(UsersEditContext);
    return (
        <Col {...columnWidths}>
            <Controller
                control={context.form.control}
                name={name}
                render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Field
                        name={name}
                        title={title}
                        Component={Component}
                        errors={[]}
                        isCreate={context?.create}
                        schemaProperties={{}}
                        onBlur={onBlur}
                        inputRef={ref}
                        onChange={onChange}
                        value={value}
                        {...props}
                    />
                )}
            />
        </Col>
    );
};
