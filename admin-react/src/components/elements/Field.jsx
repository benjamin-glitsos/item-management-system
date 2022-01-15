import styled from "styled-components";
import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import formatNull from "%/utilities/formatNull";

const RequiredAsterisk = () => <Asterisk> *</Asterisk>;

export default ({ key, Component, columnWidths, isControlled, ...props }) => {
    const fieldId = `Field/${key}`;
    const errorId = `Field/Error/${key}`;
    const schemaProperties = context?.schema?.properties;
    const title = schemaProperties?.[key]?.title;
    const isRequired = schemaProperties?.[key]?.required;
    const fieldErrors = context.form.formState.errors?.[key];
    const placeholder = formatNull();

    {
        /* const Element = ({ ...props }) => (<Component id={fieldId} placeholder={placeholder} {context.form.register(name)} {...props} />) */
    }

    return (
        <Col {...columnWidths}>
            <Styles>
                <Label htmlFor={fieldId}>
                    {title}
                    {isRequired && <RequiredAsterisk />}
                </Label>
                {/* {!isControlled ?  */}
                {/*     <Element /> :  */}
                {/*         ( */}
                {/*             <Controller */}
                {/*                 control={context.form.control} */}
                {/*                 name={name} */}
                {/*             render={({ field: { onChange, onBlur, value, ref } }) => ( */}
                {/* <Element */}
                {/*     onBlur={onBlur} */}
                {/*     inputRef={ref} */}
                {/*     onChange={onChange} */}
                {/*     isDisabled={isDisabled} */}
                {/*     value={value} */}
                {/* /> */}
                {/*                 )} */}
                {/*             </Controller> */}
                {/*         ) */}
                {/* } */}
                {fieldErrors && (
                    <Errors>
                        {fieldErrors.map((error, i) => (
                            <li key={`${errorId}/${i}`}>{error}</li>
                        ))}
                    </Errors>
                )}
            </Styles>
        </Col>
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
