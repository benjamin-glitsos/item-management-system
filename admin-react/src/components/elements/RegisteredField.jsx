import Field from "elements/Field";

export default ({ key, Component, columnWidths, ...props }) => (
    <Field
        key={key}
        Component={Component}
        columnWidths={columnWidths}
        context={context}
        {...props}
    />
);
