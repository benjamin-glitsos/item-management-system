import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import Textfield from "@atlaskit/textfield";
import { useFlags } from "@atlaskit/flag";
import "react-mde/lib/styles/css/react-mde-all.css";
import { useHistory } from "react-router-dom";
import PageContainer from "%/components/Page/PageContainer";
import OpenContainer from "%/components/Open/OpenContainer";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import Open from "%/components/Open/Open";
import config from "%/config";

export default () => {
    const { username } = useParams();

    const keyField = "username";
    const [nameSingular, namePlural] = config.names.users;
    const action = config.actions.EDIT;
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A ${nameSingular} in the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const openContainer = OpenContainer({
        action,
        key: username,
        nameSingular,
        namePlural,
        keyField,
        formFields: [
            "username",
            "email_address",
            "first_name",
            "last_name",
            "other_names",
            "additional_notes"
        ],
        optionalFields: ["other_names", "additional_notes"]
    });

    const pageContext = {
        ...pageContainer,
        ...openContainer
    };

    return (
        <Open context={pageContext}>
            <RegisteredField
                name="username"
                title="Username"
                sm={6}
                Component={Textfield}
            />
            <RegisteredField
                name="email_address"
                title="Email address"
                sm={6}
                Component={Textfield}
            />
            <RegisteredField
                name="first_name"
                title="First name"
                sm={4}
                Component={Textfield}
            />
            <RegisteredField
                name="last_name"
                title="Last name"
                sm={4}
                Component={Textfield}
            />
            <RegisteredField
                name="other_names"
                title="Other names"
                sm={4}
                Component={Textfield}
            />
            <ControlledField
                name="additional_notes"
                title="Additional notes"
                sm={12}
                Component={MarkdownTextarea}
            />
        </Open>
    );
};
