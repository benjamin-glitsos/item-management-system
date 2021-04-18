import { Controller } from "react-hook-form";
import Field from "%/components/Field";

export default ({ name, title, Component, errors, control }) => (
    <Controller
        control={control}
        name={name}
        render={({ field: { onChange, onBlur, value, ref } }) => (
            <Field
                name={name}
                title={title}
                Component={Component}
                errors={errors}
                onBlur={onBlur}
                inputRef={ref}
                onChange={onChange}
                value={value}
            />
        )}
    />
);
