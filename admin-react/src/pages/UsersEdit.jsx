import { createContext, useState, useEffect } from "react";
import R from "ramda";
import { useHistory, useParams } from "react-router-dom";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "hooks/useEdit";
import useQueries from "hooks/useQueries";
import useMutation from "hooks/useMutation";
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

    // TODO: make mutation work by passing arguments properly.
    const submitMutation = useMutation(
        "PATCH",
        [user.namePlural, username],
        body
    );

    console.log(submitMutation({ other_names: "wow" }));

    useEffect(async () => {
        for await (const query of queries) {
            query.refetch();
        }
    }, []);

    const [schema, data] = queries.map(x => x?.data?.data?.data);

    const page = usePage({
        history,
        action: edit.action,
        key: data?.[user.keyField],
        nameSingular: user.nameSingular,
        namePlural: user.namePlural,
        projectName: project.name
    });

    const context = {
        page,
        edit,
        schema,
        data,
        submitMutation
    };

    return (
        <QueryResult queries={queries} maxWidth={edit.maxWidth}>
            <UsersEditContext.Provider value={context}>
                <EditTemplate context={context} form={<UsersEditForm />} />
            </UsersEditContext.Provider>
        </QueryResult>
    );
};
