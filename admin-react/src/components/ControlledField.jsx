import { useContext } from "react";
import { Controller } from "react-hook-form";
import Field from "%/components/Field";
import { OpenContext } from "%/components/Open/Open";

export default ({ name, title, Component }) => {
    const context = useContext(OpenContext);
    return (
        <Controller
            control={context.control}
            name={name}
            render={({ field: { onChange, onBlur, value, ref } }) => (
                <Field
                    name={name}
                    title={title}
                    Component={Component}
                    errors={context.errors}
                    onBlur={onBlur}
                    inputRef={ref}
                    onChange={onChange}
                    value={value}
                />
            )}
        />
    );
};
