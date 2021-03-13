import { useEffect, useState, useCallback } from "react";
import { useImmer } from "use-immer";
import axios from "axios";
import pipe from "pipe-functions";
import { Checkbox } from "@atlaskit/checkbox";
import TableActionsMenu from "%/presenters/TableActionsMenu";
import RemoveAllSelected from "%/presenters/RemoveAllSelected";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";
import friendlyName from "%/utilities/friendlyName";
import Error from "%/utilities/Error";
import config from "%/config";

export default () => {
    const apiUrl = config.serverUrl + "v1/users/";

    const [state, setState] = useImmer({
        request: {
            body: {
                page_number: 1,
                page_length: 10
            }
        },
        response: {
            data: {
                items: []
            },
            errors: []
        },
        isLoading: false,
        selected: []
    });

    const requestListUsers = body =>
        axios({
            method: "REPORT",
            url: apiUrl,
            data: body
        });

    const requestDeleteUsers = (method, usernames) =>
        axios({
            method: "DELETE",
            url: apiUrl,
            data: {
                method,
                usernames
            }
        });

    const setLoading = bool =>
        setState(draft => {
            draft.isLoading = bool;
        });

    const setResponse = response =>
        setState(draft => {
            draft.response = Object.assign(draft.response, response.data);
        });

    const setResponseErrors = errors =>
        setState(draft => {
            draft.response.errors = errors;
        });

    const setPageNumber = (event, page, analyticsEvent) =>
        setState(draft => {
            draft.request.body.page_number = page;
        });

    const setPageLength = selectedOption =>
        setState(draft => {
            draft.request.body.page_number = 1;
            draft.request.body.page_length = selectedOption.value;
        });

    const setSelected = key =>
        setState(draft => {
            if (!draft.selected.includes(key)) {
                draft.selected.push(key);
            } else {
                draft.selected = draft.selected.filter(x => x !== key);
            }
        });

    const setRemoveAllSelected = () =>
        setState(draft => {
            draft.selected = [];
        });

    const listUsersAction = async () => {
        setLoading(true);
        try {
            const response = await requestListUsers(state.request.body);
            setResponse(response);
        } catch (error) {
            setResponseErrors([
                new Error("error", "Error", "Something went wrong.")
            ]);
        }
        setLoading(false);
    };

    const handleErrorsAction = () =>
        state.response.errors.forEach(console.error);

    const deleteUsersAction = async (method, usernames) => {
        await requestDeleteUsers(method, usernames);
        await listUsersAction();
        setRemoveAllSelected();
    };

    useEffect(listUsersAction, [state.request]);

    useEffect(handleErrorsAction, [state.response.errors]);

    const head = {
        cells: [
            {
                key: "isSelected",
                content: (
                    <RemoveAllSelected
                        doesSelectionExist={state.selected.length > 0}
                        action={setRemoveAllSelected}
                    />
                ),
                isSortable: false,
                width: 3
            },
            {
                key: "username",
                content: "Username",
                isSortable: false
            },
            {
                key: "name",
                content: "Name",
                isSortable: false
            },
            {
                key: "emailAddress",
                content: "Email Address",
                isSortable: false
            },
            {
                key: "createdAt",
                content: "Created At",
                isSortable: false,
                width: 15
            },
            {
                key: "editedAt",
                content: "Edited At",
                isSortable: false,
                width: 15
            },
            {
                key: "actions",
                content: null,
                isSortable: false,
                width: 10
            }
        ]
    };

    const rows = state.response.data.items.map((row, i) => ({
        key: `UsersTable/Row/${i}`,
        cells: pipe(
            row,
            ([
                username,
                emailAddress,
                firstName,
                lastName,
                otherNames,
                createdAt,
                editedAt
            ]) => [
                username,
                friendlyName(firstName, lastName, otherNames),
                emailAddress,
                friendlyDate(createdAt),
                friendlyDate(fromMaybe(editedAt))
            ],
            row => [
                <Checkbox
                    isChecked={state.selected.includes(row[0])}
                    onChange={() => setSelected(row[0])}
                />,
                ...row,
                <TableActionsMenu
                    softDeleteAction={() => deleteUsersAction("soft", [row[0]])}
                    hardDeleteAction={() => deleteUsersAction("hard", [row[0]])}
                />
            ],
            x =>
                x.map((x, i) => ({
                    key: `UsersTable/Cell/${i}`,
                    content: x
                }))
        )
    }));

    return {
        head,
        rows,
        state,
        setPageNumber,
        setPageLength,
        deleteUsersAction
    };
};
