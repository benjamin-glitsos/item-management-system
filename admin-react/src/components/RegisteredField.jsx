import { useContext } from "react";
import Field from "%/components/Field";
import { OpenContext } from "%/components/Open/Open";

export default ({ name, title, Component, ...props }) => {
    const context = useContext(OpenContext);
    return (
        <Field
            name={name}
            title={title}
            Component={Component}
            errors={context.errors}
            additionalProps={context.register(name)}
        />
    );
};
