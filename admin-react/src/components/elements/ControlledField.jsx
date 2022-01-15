import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import Field from "elements/Field";

export default ({
    name,
    title,
    Component,
    columnWidths,
    isDisabled,
    ...props
}) => (
    <Col {...columnWidths}>
        <Controller
            control={context.form.control}
            name={name}
            render={({ field: { onChange, onBlur, value, ref } }) => (
                <Field
                    name={name}
                    title={title}
                    Component={Component}
                    errors={context.form.formState.errors}
                    isCreate={!!context?.create}
                    schemaProperties={context?.schema?.properties}
                    onBlur={onBlur}
                    inputRef={ref}
                    onChange={onChange}
                    isDisabled={isDisabled}
                    value={value}
                    {...props}
                />
            )}
        />
    </Col>
);
