import { useEffect, useState } from "react";
import axios from "axios";
import pipe from "pipe-functions";
import { Checkbox } from "@atlaskit/checkbox";
import TableActionsMenu from "%/presenters/TableActionsMenu";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";
import friendlyName from "%/utilities/friendlyName";

export default () => {
    const [state, setState] = useState({
        request: {
            method: "REPORT",
            url: "http://localhost:4073/api/rest/v1/users/",
            data: {
                page_number: 1,
                page_length: 10
            }
        },
        response: []
    });

    useEffect(async () => {
        const response = await axios(state.request);
        setState(state => ({ ...state, response: response.data }));
    }, [state.request]);

    const head = {
        cells: [
            {
                key: "isSelected",
                content: null,
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

    const rows = state.response.map((row, i) => ({
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
            row => [<Checkbox />, ...row, <TableActionsMenu />],
            x =>
                x.map((x, i) => ({
                    key: `UsersTable/Cell/${i}`,
                    content: x
                }))
        )
    }));

    return { state, setState, head, rows };
};
