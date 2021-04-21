import { useContext } from "react";
import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import Field from "%/components/Field";
import { OpenContext } from "%/components/Open/Open";

export default ({ name, title, sm, Component }) => {
    const context = useContext(OpenContext);
    return (
        <Col sm={sm}>
            <Controller
                control={context.control}
                name={name}
                render={({ field: { onChange, onBlur, value, ref } }) => (
                    <Field
                        name={name}
                        title={title}
                        Component={Component}
                        errors={context.errors}
                        action={context.action}
                        schemaProperties={context.state.schema.properties}
                        onBlur={onBlur}
                        inputRef={ref}
                        onChange={onChange}
                        value={value}
                    />
                )}
            />
        </Col>
    );
};
