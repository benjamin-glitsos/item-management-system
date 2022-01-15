import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import Textfield from "@atlaskit/textfield";
import "react-mde/lib/styles/css/react-mde-all.css";
import PageContainer from "%/components/PageContainer";
import OpenContainer from "%/components/OpenContainer";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import FormSubheading from "%/components/FormSubheading";
import Open from "%/components/Open";
import config from "%/config";

export default ({ action }) => {
    const isCreate = action === "create";

    const { username } = useParams();

    const keyField = "username";
    const [nameSingular, namePlural] = config.names.users;
    const title = isCreate
        ? titleCase(`${action} ${nameSingular}`)
        : titleCase(namePlural);
    const slug = namePlural;
    const description = isCreate
        ? `Create a ${nameSingular} in the ${
              process.env.PROJECT_NAME || "Item Management System"
          }.`
        : `A ${nameSingular} in the ${
              process.env.PROJECT_NAME || "Item Management System"
          }.`;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description
    });

    const openContainer = OpenContainer({
        action,
        isCreate,
        key: username,
        nameSingular,
        namePlural,
        keyField
    });

    const pageContext = {
        ...pageContainer,
        ...openContainer
    };

    return (
        <Open context={pageContext}>
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
        </Open>
    );
};
