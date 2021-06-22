import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import Textfield from "@atlaskit/textfield";
import "react-mde/lib/styles/css/react-mde-all.css";
import PageContainer from "%/components/Page/PageContainer";
import OpenContainer from "%/components/Open/OpenContainer";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import FormSubheading from "%/components/FormSubheading";
import Open from "%/components/Open/Open";
import config from "%/config";

export default ({ action }) => {
    const isCreate = action === "create";

    const { key } = useParams();

    const keyField = "key";
    const [nameSingular, namePlural] = config.names.items;
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
        key,
        keyField,
        nameSingular,
        namePlural
    });

    const pageContext = {
        ...pageContainer,
        ...openContainer
    };

    return (
        <Open context={pageContext}>
            <FormSubheading level={3}>Details</FormSubheading>
            <RegisteredField
                name="key"
                title="Key"
                Component={Textfield}
                columnWidths={{ lg: 6 }}
            />
            <RegisteredField
                name="name"
                title="Name"
                Component={Textfield}
                columnWidths={{ lg: 6 }}
            />
            <ControlledField
                name="description"
                title="Description"
                Component={MarkdownTextarea}
                columnWidths={{ sm: 12 }}
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
