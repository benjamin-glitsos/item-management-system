import { useEffect, useState } from "react";
import FullwidthLayout from "../components/FullwidthLayout";
import BreadcrumbBar from "../components/BreadcrumbBar";
import TableActionsMenu from "../components/TableActionsMenu";
import TableStatusBar from "../components/TableStatusBar";
import NoData from "../components/NoData";
import ActionsBar from "../components/ActionsBar";
import fromMaybe from "../utilities/fromMaybe";
import friendlyDate from "../utilities/friendlyDate";
import friendlyName from "../utilities/friendlyName";
import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import { Checkbox } from "@atlaskit/checkbox";
import axios from "axios";
import { pipeline, pipe } from "ts-pipe-compose";

export default () => {
    const [data, setData] = useState({ data: [] });

    useEffect(async () => {
        const result = await axios({
            method: "REPORT",
            url: "http://localhost:4073/api/rest/v1/users/",
            data: {
                page_number: 1,
                page_length: 10
            }
        });

        console.log(result.data);
        setData(result.data);
    }, []);

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

    const toCell = (x, i) => ({
        key: `UsersPage/Table/Cell/${i}`,
        content: x
    });

    const rowActions = row => [<Checkbox />, ...row, <TableActionsMenu />];

    const columnTransformations = ([
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
    ];

    const rows = data.data.map((row, i) => ({
        key: `UsersPage/Table/Row/${i}`,
        cells: pipeline(columnTransformations, rowActions, x => x.map(toCell))(
            row
        )
    }));

    return (
        <FullwidthLayout>
            <PageHeader
                breadcrumbs={
                    <BreadcrumbBar
                        breadcrumbs={[
                            ["Home", "/"],
                            ["Users", "/users"]
                        ]}
                    />
                }
                actions={<ActionsBar />}
                bottomBar={
                    <TableStatusBar
                        placeholder={
                            "A list of users who have access to log in to this system."
                        }
                        numberOfSelected={0}
                    />
                }
            >
                Users
            </PageHeader>
            <DynamicTable
                head={head}
                rows={rows}
                rowsPerPage={10}
                defaultPage={1}
                loadingSpinnerSize="large"
                isLoading={false}
                isFixedSize
                defaultSortKey="term"
                defaultSortOrder="ASC"
                onSetPage={() => {}}
                emptyView={<NoData />}
            />
        </FullwidthLayout>
    );
};