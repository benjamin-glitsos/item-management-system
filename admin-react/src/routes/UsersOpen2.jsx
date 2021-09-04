import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import useOpen from "%/hooks/useOpen";
import useProject from "%/hooks/useProject";
import useUser from "%/hooks/useUser";
import PageLayout from "%/components/PageLayout";
import LoadingSpinner from "%/components/LoadingSpinner";
import joinTitle from "%/utilities/joinTitle";

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
    const pageAction = joinTitle([
        titleCase(`${openData.action} ${user.nameSingular}`),
        openRes[user.keyField]
    ]);
    const pageTitle = joinTitle([pageAction, project.name]);
    const pageDescription = `A ${user.nameSingular} in the ${project.name}.`;

    return (
        <PageLayout title={pageTitle} description={"wow"}>
            <h1>{pageAction}</h1>
        </PageLayout>
    );
};
