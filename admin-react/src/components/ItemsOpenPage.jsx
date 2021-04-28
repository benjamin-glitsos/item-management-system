import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import Textfield from "@atlaskit/textfield";
import "react-mde/lib/styles/css/react-mde-all.css";
import PageContainer from "%/components/Page/PageContainer";
import OpenContainer from "%/components/Open/OpenContainer";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
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
            <RegisteredField
                name="key"
                title="Key"
                sm={6}
                Component={Textfield}
            />
            <RegisteredField
                name="name"
                title="Name"
                sm={6}
                Component={Textfield}
            />
            <ControlledField
                name="description"
                title="Description"
                sm={12}
                Component={MarkdownTextarea}
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
