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
import useUser from "%/hooks/useUser";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";
import Page from "%/components/Page";
import Content from "%/components/Content";
import EditSidebar from "%/components/EditSidebar";
import Form from "%/components/Form";
import LoadingSpinner from "%/components/LoadingSpinner";
import RegisteredField2 from "%/components/RegisteredField2";
import ControlledField2 from "%/components/ControlledField2";
import FormSubheading from "%/components/FormSubheading";
import ErrorBanner from "%/components/ErrorBanner";
import someProp from "%/utilities/someProp";
import getQueryData from "%/utilities/getQueryData";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const user = useUser();

    const queries = useQueriesClient({
        paths: [
            [`schemas/${edit.action}-${user.namePlural}`],
            [user.namePlural, username]
        ],
        config: { refetchOnWindowFocus: false }
    });

    const [schemaData, usersData] = queries.map(getQueryData);

    const form = useForm({
        resolver: useYupSchemaResolver({ schemaData, originalData: usersData })
    });

    const page = usePage({
        history,
        action: edit.action,
        key: usersData?.[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const breadcrumbs = [page.breadcrumb, user.breadcrumb, edit.breadcrumb];

    const { mutate, isLoading: isMutateLoading } = useEditClient({
        path: [user.namePlural, username],
        context: {
            namePlural: user.namePlural,
            keyField: user.keyField,
            history,
            originalData: usersData
        }
    });

    const context = {
        page,
        edit,
        breadcrumbs,
        form,
        mutate,
        isMutateLoading,
        schemaData,
        usersData
    };

    if (true) {
        // someProp("isLoading", queries)
        return <LoadingSpinner />;
    }

    if (someProp("isError", queries)) {
        return <ErrorBanner />;
    }

    return (
        <UsersEditContext.Provider value={context}>
            <Page
                title={page.tabTitle}
                description={page.pageDescription}
                breadcrumbs={breadcrumbs}
                maxWidth={edit.maxWidth}
            >
                <SidebarLayout sidebar={<EditSidebar data={usersData} />}>
                    <Form context={UsersEditContext}>
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
            </Page>
        </UsersEditContext.Provider>
    );
};
