import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useForm } from "react-hook-form";
import SidebarLayout from "%/components/SidebarLayout";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "%/hooks/useEdit";
import useQueriesClient from "%/hooks/useQueriesClient";
import useEditClient from "%/hooks/useEditClient";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import useYupSchemaResolver from "%/hooks/useYupSchemaResolver";
import Page2 from "%/components/Page2";
import EditSidebar2 from "%/components/EditSidebar2";
import LoadingBanner from "%/components/LoadingBanner";
import ErrorBanner from "%/components/ErrorBanner";
import getQueryData from "%/utilities/getQueryData";
import UsersForm from "%/components/UsersForm";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();
    const history = useHistory();

    const project = useProject();
    const edit = useEdit();
    const entity = useUser();

    const query = useQueriesClient({
        paths: [[`schemas/${edit.action}-${entity.namePlural}`]],
        config: { refetchOnWindowFocus: false }
    })[0];

    const schemaData = getQueryData(query);

    const form = useForm({
        resolver: useYupSchemaResolver({ schemaData })
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
        path: [entity.namePlural, username],
        context: {
            namePlural: entity.namePlural,
            keyField: entity.keyField,
            history,
            originalData: entityData,
            setValue: form.setValue
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

    if (query?.isLoading) {
        return <LoadingBanner />;
    }

    if (query?.isError) {
        return <ErrorBanner />;
    }

    return (
        <UsersEditContext.Provider value={context}>
            <Page2 context={UsersEditContext}>
                <SidebarLayout
                    sidebar={<EditSidebar2 context={UsersEditContext} />}
                >
                    <UsersForm context={UsersEditContext} />
                </SidebarLayout>
            </Page2>
        </UsersEditContext.Provider>
    );
};
