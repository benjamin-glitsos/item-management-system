import { Fragment } from "react";

export default ({
    name,
    title,
    Component,
    errors,
    additionalProps,
    ...props
}) => {
    const fieldId = `Field/${name}`;
    const errorId = `Field/Error/${name}`;
    const fieldErrors = errors?.[name];
    return (
        <Fragment>
            <label htmlFor={fieldId}>{title}</label>
            <Component id={fieldId} {...additionalProps} {...props} />
            {fieldErrors && (
                <ul>
                    {fieldErrors.map((error, i) => (
                        <li key={`${errorId}/${i}`}>{error}</li>
                    ))}
                </ul>
            )}
        </Fragment>
    );
};

// const Label = styled.label`
//     ${props => props.isChanged && "font-style: italic;"}
// `;
