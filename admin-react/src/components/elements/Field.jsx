import styled from "styled-components";
import formatNull from "%/utilities/formatNull";

const RequiredAsterisk = () => <Asterisk> *</Asterisk>;

export default ({
    key,
    title,
    Component,
    errors,
    isCreate,
    isDisabled,
    schemaProperties,
    additionalProps,
    ...props
}) => {
    const fieldId = `Field/${key}`;
    const errorId = `Field/Error/${key}`;
    const fieldErrors = errors?.[key];
    const isRequired = isCreate && schemaProperties?.[key]?.required;
    const placeholder = isCreate ? "" : formatNull();
    return (
        <Styles>
            <Label htmlFor={fieldId}>
                {title}
                {isRequired && <RequiredAsterisk />}
            </Label>
            <Component
                id={fieldId}
                placeholder={placeholder}
                isDisabled={isDisabled}
                {...additionalProps}
                {...props}
            />
            {fieldErrors && (
                <Errors>
                    {fieldErrors.map((error, i) => (
                        <li key={`${errorId}/${i}`}>{error}</li>
                    ))}
                </Errors>
            )}
        </Styles>
    );
};

const Styles = styled.div`
    margin-top: 8px;
`;

const Label = styled.label`
    font-size: 0.857143em;
    line-height: 1.33333;
    color: rgb(107, 119, 140);
    font-weight: 600;
    margin-bottom: 4px;
    margin-top: 0px;
`;

const Errors = styled.ul`
    padding-left: 24px;
    font-size: 0.857143em;
    line-height: 1.33333;
    color: rgb(222, 53, 11);
    margin-top: 4px;
    margin-bottom: 4px;
`;

const Asterisk = styled.span`
    color: rgb(222, 53, 11);
`;
