import Textfield from "@atlaskit/textfield";
import Form from "modules/Form";
import FormSubheading from "elements/FormSubheading";
import RegisteredField from "elements/RegisteredField";
import ControlledField from "elements/ControlledField";
import MarkdownTextarea from "elements/MarkdownTextarea";

export default ({ page, edit, form }) => {
    return (
        <Form page={page} edit={edit} form={form}>
            <FormSubheading level={3}>Details</FormSubheading>
            <RegisteredField
                name="username"
                title="Username"
                Component={Textfield}
                columnWidths={{ lg: 6 }}
            />
            <RegisteredField
                name="email_address"
                title="Email address"
                Component={Textfield}
                columnWidths={{ lg: 6 }}
            />
            <RegisteredField
                name="first_name"
                title="First name"
                Component={Textfield}
                columnWidths={{ lg: 4 }}
            />
            <RegisteredField
                name="last_name"
                title="Last name"
                Component={Textfield}
                columnWidths={{ lg: 4 }}
            />
            <RegisteredField
                name="other_names"
                title="Other names"
                Component={Textfield}
                columnWidths={{ lg: 4 }}
            />
            <FormSubheading level={3}>Misc.</FormSubheading>
            <ControlledField
                name="additional_notes"
                title="Additional notes"
                Component={MarkdownTextarea}
                columnWidths={{ sm: 12 }}
            />
        </Form>
    );
};
