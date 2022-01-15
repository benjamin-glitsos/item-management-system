import styled from "styled-components";
import { Controller } from "react-hook-form";
import { Col } from "react-flexbox-grid";
import formatNull from "%/utilities/formatNull";
import Textfield from "@atlaskit/textfield";
import MarkdownTextarea from "elements/MarkdownTextarea";
import nullToEmptyStr from "utilities/nullToEmptyStr";

const RequiredAsterisk = () => <Asterisk> *</Asterisk>;

export default ({ name, columnWidths, isControlled, context, ...props }) => {
    const fieldId = `Field/${name}`;
    const errorId = `Field/Error/${name}`;
    const schemaProperties = context?.schemaData?.properties;
    const title = schemaProperties?.[name]?.title;
    const isRequired = schemaProperties?.[name]?.required;
    const fieldErrors = context.form.formState.errors[name];
    const placeholder = formatNull();
    const isDisabled = context.mutation.isLoading;
    const value = context.itemData[name];

    context.form.setValue(name, nullToEmptyStr(value));

    return (
        <Col {...columnWidths}>
            <Styles>
                <Label htmlFor={fieldId}>
                    {title}
                    {isRequired && <RequiredAsterisk />}
                </Label>
                {!isControlled ? (
                    <Textfield
                        key={fieldId}
                        name={name}
                        placeholder={placeholder}
                        isDisabled={isDisabled}
                        {...context.form.register(name)}
                        {...props}
                    />
                ) : (
                    <Controller
                        control={context.form.control}
                        name={name}
                        render={({
                            field: { onChange, onBlur, value, ref }
                        }) => (
                            <MarkdownTextarea
                                key={fieldId}
                                name={name}
                                placeholder={placeholder}
                                onChange={onChange}
                                onBlur={onBlur}
                                value={value}
                                inputRef={ref}
                                {...props}
                            />
                        )}
                    />
                )}
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
