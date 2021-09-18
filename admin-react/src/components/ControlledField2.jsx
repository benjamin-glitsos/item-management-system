import { useContext } from "react";
import { Controller } from "react-hook-form";
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
            <Controller
                control={cx.form.control}
                name={name}
                render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Field
                        name={name}
                        title={title}
                        Component={Component}
                        errors={cx.form.formState?.errors}
                        isCreate={!!cx?.create}
                        schemaProperties={cx.schemaData.properties}
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
