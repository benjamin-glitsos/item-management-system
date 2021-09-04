import { useParams } from "react-router-dom";
import useOpen from "%/hooks/useOpen";
import useProject from "%/hooks/useProject";
import usePage from "%/hooks/usePage";
import useUser from "%/hooks/useUser";
import PageLayout from "%/components/PageLayout";
import LoadingSpinner from "%/components/LoadingSpinner";

export default () => {
    const { username } = useParams();
    const project = useProject();
    const user = useUser();

    const {
        isLoading,
        isError,
        error,
        data: { openResponse, openData }
    } = useOpen({
        path: [user.namePlural, username]
    });

    if (isLoading) {
        return <LoadingSpinner />;
    }

    if (isError) {
        return <div>Error</div>;
    }

    const openRes = openResponse.data.data;

    const page = usePage({
        action: openData.action,
        key: openRes[user.keyField],
        userNameSingular: user.nameSingular,
        projectName: project.name
    });

    return (
        <PageLayout title={page.pageTitle} description={page.pageDescription}>
            <h1>{page.actionTitle}</h1>
        </PageLayout>
    );
};
