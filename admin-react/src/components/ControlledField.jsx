import { useContext } from "react";
import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import Field from "elements/Field";
import { OpenContext } from "%/components/Open";

export default ({ name, title, Component, columnWidths, ...props }) => {
    const context = useContext(OpenContext);
    return (
        <Col {...columnWidths}>
            <Controller
                control={context.control}
                name={name}
                render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Field
                        name={name}
                        title={title}
                        Component={Component}
                        errors={context.errors}
                        isCreate={context.isCreate}
                        schemaProperties={context.state.schema.properties}
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
