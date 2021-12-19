import { createContext } from "react";
import { useHistory, useParams } from "react-router-dom";
import "react-mde/lib/styles/css/react-mde-all.css";
import someProp from "utilities/someProp";
import useEdit from "hooks/useEdit";
import useQueries from "hooks/useQueries";
import useProject from "hooks/useProject";
import usePage from "hooks/usePage";
import useUser from "hooks/useUser";
import LoadingSpinnerContent from "modules/LoadingSpinnerContent";
import ErrorMessageContent from "modules/ErrorMessageContent";
import UsersEditForm from "modules/UsersEditForm";
import EditTemplate from "templates/EditTemplate";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();

    const history = useHistory();

    const project = useProject();

    const edit = useEdit();

    const user = useUser();

    const queries = useQueries(
        [
            `schemas/${edit.action}-${user.namePlural}`,
            `${user.namePlural}/${username}`
        ],
        {
            refetchOnMount: false,
            refetchOnWindowFocus: false,
            refetchOnReconnect: false
        }
    );

    // const editClient = body =>
    //     useEditClient({
    //         path: [user.namePlural, username],
    //         body
    //     });
    //     TODO: make this mutation hook useEditClient again. I accidentally deleted it

    const [schema, data] = queries.map(x => x?.data?.data?.data);

    if (someProp("isLoading", queries)) {
        return <LoadingSpinnerContent maxWidth={edit.maxWidth} />;
    }

    if (someProp("isError", queries)) {
        return <ErrorMessageContent maxWidth={edit.maxWidth} />;
    }

    const page = usePage({
        history,
        action: edit.action,
        key: data[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const context = {
        page,
        edit,
        schema,
        data
    };

    return (
        <UsersEditContext.Provider value={context}>
            <EditTemplate context={context} form={<UsersEditForm />} />
        </UsersEditContext.Provider>
    );
};
