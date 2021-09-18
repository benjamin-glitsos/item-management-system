import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import Textfield from "@atlaskit/textfield";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import SidebarLayout from "%/components/SidebarLayout";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "%/hooks/useEdit";
import useQueriesClient from "%/hooks/useQueriesClient";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useItem from "%/hooks/useItem";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";
import Page2 from "%/components/Page2";
import Content from "%/components/Content";
import EditSidebar2 from "%/components/EditSidebar2";
import Form from "%/components/Form";
import LoadingBanner from "%/components/LoadingBanner";
import RegisteredField2 from "%/components/RegisteredField2";
import ControlledField2 from "%/components/ControlledField2";
import FormSubheading from "%/components/FormSubheading";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";
import getQueryData from "%/utilities/getQueryData";

export const Context = createContext();

export default () => {
    const { key } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const entity = useItem();

    const queries = useQueriesClient({
        paths: [
            [`schemas/${edit.action}-${entity.namePlural}`],
            [entity.namePlural, key]
        ],
        config: { refetchOnWindowFocus: false }
    });

    const [schemaData, entityData] = queries.map(getQueryData);

    const form = useForm({
        resolver: useYupSchemaResolver({ schemaData, originalData: entityData })
    });

    const page = usePage({
        history,
        action: edit.action,
        key: entityData?.[entity.keyField],
        nameSingular: entity.nameSingular,
        namePlural: entity.namePlural,
        projectName: project.name
    });

    const breadcrumbs = [page.breadcrumb, entity.breadcrumb, edit.breadcrumb];

    const mutation = useEditClient({
        path: [entity.namePlural, key],
        context: {
            namePlural: entity.namePlural,
            keyField: entity.keyField,
            history,
            originalData: entityData
        }
    });

    const context = {
        page,
        edit,
        entity,
        breadcrumbs,
        form,
        mutation,
        schemaData,
        entityData
    };

    if (someProp("isLoading", queries)) {
        return <LoadingBanner />;
    }

    if (someProp("isError", queries)) {
        return <ErrorBanner />;
    }

    return (
        <Context.Provider value={context}>
            <Page2 context={Context}>
                <SidebarLayout sidebar={<EditSidebar2 context={Context} />}>
                    <Form context={Context}>
                        <FormSubheading level={3}>Details</FormSubheading>
                        <RegisteredField2
                            name="username"
                            title="Username"
                            Component={Textfield}
                            columnWidths={{ lg: 6 }}
                        />
                        <RegisteredField2
                            name="email_address"
                            title="Email address"
                            Component={Textfield}
                            columnWidths={{ lg: 6 }}
                        />
                        <RegisteredField2
                            name="first_name"
                            title="First name"
                            Component={Textfield}
                            columnWidths={{ lg: 4 }}
                        />
                        <RegisteredField2
                            name="last_name"
                            title="Last name"
                            Component={Textfield}
                            columnWidths={{ lg: 4 }}
                        />
                        <RegisteredField2
                            name="other_names"
                            title="Other names"
                            Component={Textfield}
                            columnWidths={{ lg: 4 }}
                        />
                        <FormSubheading level={3}>Misc.</FormSubheading>
                        <ControlledField2
                            name="additional_notes"
                            title="Additional notes"
                            Component={MarkdownTextarea}
                            columnWidths={{ sm: 12 }}
                        />
                    </Form>
                </SidebarLayout>
            </Page2>
        </Context.Provider>
    );
};
