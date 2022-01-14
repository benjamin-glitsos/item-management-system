import { createContext, useState, useEffect } from "react";
import R from "ramda";
import { useHistory, useParams } from "react-router-dom";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "hooks/useEdit";
import useQueries from "hooks/useQueries";
import useProject from "hooks/useProject";
import usePage from "hooks/usePage";
import useUser from "hooks/useUser";
import QueryResult from "modules/QueryResult";
import UsersEditForm from "modules/UsersEditForm";
import EditTemplate from "templates/EditTemplate";
import unspecifiedErrorToast from "utilities/unspecifiedErrorToast";

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
            enabled: false
        }
    );

    useEffect(async () => {
        for await (const query of queries) {
            query.refetch();
        }
    }, []);

    return (
        <QueryResult queries={queries} maxWidth={edit.maxWidth}>
            <p>success</p>
        </QueryResult>
    );

    // const editClient = body =>
    //     useEditClient({
    //         path: [user.namePlural, username],
    //         body
    //     });
    //     TODO: make this mutation hook useEditClient again. I accidentally deleted it

    // const page = usePage({
    //     history,
    //     action: edit.action,
    //     key: data?.[user.keyField],
    //     nameSingular: user.nameSingular,
    //     namePlural: user.namePlural,
    //     projectName: project.name
    // });

    // const context = {
    //     page,
    //     edit,
    //     schema,
    //     data,
    //     queries
    // };

    // return (
    //     <UsersEditContext.Provider value={context}>
    //         <EditTemplate context={context} form={<UsersEditForm />} />
    //     </UsersEditContext.Provider>
    // );
};
