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
    const { key } = useParams();

    const [nameSingular, namePlural] = config.names.items;
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
        key,
        nameSingular,
        namePlural,
        formFields: ["key", "name", "description", "additional_notes"],
        optionalFields: ["description", "additional_notes"]
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
                isDisabled={true}
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
