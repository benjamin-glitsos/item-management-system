import Form from "%/components/Form";
import Textfield from "@atlaskit/textfield";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import RegisteredField2 from "%/components/RegisteredField2";
import ControlledField2 from "%/components/ControlledField2";
import FormSubheading from "%/components/FormSubheading";

export default ({ context }) => (
    <Form context={context}>
        <FormSubheading level={3}>Details</FormSubheading>
        <RegisteredField2
            name="username"
            title="Username"
            Component={Textfield}
            columnWidths={{ lg: 6 }}
            context={context}
        />
        <RegisteredField2
            name="email_address"
            title="Email address"
            Component={Textfield}
            columnWidths={{ lg: 6 }}
            context={context}
        />
        <RegisteredField2
            name="first_name"
            title="First name"
            Component={Textfield}
            columnWidths={{ lg: 4 }}
            context={context}
        />
        <RegisteredField2
            name="last_name"
            title="Last name"
            Component={Textfield}
            columnWidths={{ lg: 4 }}
            context={context}
        />
        <RegisteredField2
            name="other_names"
            title="Other names"
            Component={Textfield}
            columnWidths={{ lg: 4 }}
            context={context}
        />
        <FormSubheading level={3}>Misc.</FormSubheading>
        <ControlledField2
            name="additional_notes"
            title="Additional notes"
            Component={MarkdownTextarea}
            columnWidths={{ sm: 12 }}
            context={context}
        />
    </Form>
);
