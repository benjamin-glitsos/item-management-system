import { useParams } from "react-router-dom";
import useOpen from "%/hooks/useOpen";
import useOpenService from "%/hooks/useOpenService";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import Page from "%/components/Page";
import Content from "%/components/Content";
import OpenLayout from "%/components/OpenLayout";
import LoadingSpinner from "%/components/LoadingSpinner";

export default () => {
    const { username } = useParams();
    const project = useProject();
    const open = useOpen();
    const user = useUser();

    const { isLoading, isError, error, data } = useOpenService({
        path: [user.namePlural, username]
    });

    if (isLoading) {
        return (
            <Content maxWidth={open.maxWidth}>
                <LoadingSpinner />
            </Content>
        );
    }

    if (isError) {
        return (
            <Content maxWidth={open.maxWidth}>
                <div>Error</div>
            </Content>
        );
    }

    const dataRes = data.data.data;

    const page = usePage({
        action: open.action,
        key: dataRes[user.keyField],
        userNameSingular: user.nameSingular,
        projectName: project.name
    });

    return (
        <Page
            title={page.tabTitle}
            description={page.pageDescription}
            breadcrumbs={[]}
            maxWidth={open.maxWidth}
        >
            Content
        </Page>
    );
};
