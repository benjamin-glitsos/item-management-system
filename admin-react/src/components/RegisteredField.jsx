export default ({ name, title, Component, errors, register, ...props }) => (
    <Field
        name={name}
        title={title}
        Component={Component}
        errors={errors}
        additionalProps={register(name)}
    />
);
