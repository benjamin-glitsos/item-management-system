import { createContext, useState, useEffect } from "react";
import R from "ramda";
import { useHistory, useParams } from "react-router-dom";
import "react-mde/lib/styles/css/react-mde-all.css";
import useEdit from "hooks/useEdit";
import useQueriesClient from "hooks/useQueriesClient";

import useMutationClient from "hooks/useMutationClient";
import axios from "axios";
import makeApiPath from "utilities/makeApiPath";

import useProject from "hooks/useProject";
import usePage from "hooks/usePage";
import useUser from "hooks/useUser";
import QueryResult from "modules/QueryResult";
import UsersEditForm from "modules/UsersEditForm";
import EditTemplate from "templates/EditTemplate";

export const UsersEditContext = createContext();

export default () => {
    const { username } = useParams();

    const history = useHistory();

    const project = useProject();

    const edit = useEdit();

    const user = useUser();

    const queries = useQueriesClient(
        [
            `schemas/${edit.action}-${user.namePlural}`,
            `${user.namePlural}/${username}`
        ],
        {
            enabled: false
        }
    );

    const editMutation = useMutationClient(
        "PATCH",
        `${user.namePlural}/${username}`
    );

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
        editMutation,
        queries
    };

    return (
        <QueryResult queries={queries} maxWidth={edit.maxWidth}>
            <UsersEditContext.Provider value={context}>
                <EditTemplate context={context} form={<UsersEditForm />} />
            </UsersEditContext.Provider>
        </QueryResult>
    );
};
