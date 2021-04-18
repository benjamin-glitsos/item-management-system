import styled from "styled-components";
import formatNull from "%/utilities/formatNull";

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
        <Wrapper>
            <Label htmlFor={fieldId}>{title}</Label>
            <Component
                id={fieldId}
                placeholder={formatNull()}
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
        </Wrapper>
    );
};

const Wrapper = styled.div`
    margin-top: 8px;
`;

const Label = styled.label`
    font-size: 0.857143em;
    font-style: inherit;
    line-height: 1.33333;
    color: rgb(107, 119, 140);
    font-weight: 600;
    display: inline-block;
    margin-bottom: 4px;
    margin-top: 0px;
`;

const Errors = styled.ul`
    padding-left: 25px;
    font-size: 0.857143em;
    font-style: inherit;
    line-height: 1.33333;
    font-weight: normal;
    color: rgb(222, 53, 11);
    margin-top: 4px;
    display: flex;
`;
