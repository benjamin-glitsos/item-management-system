import { useEffect, useState } from "react";
import PageMargins from "../components/PageMargins";
import BreadcrumbBar from "../components/BreadcrumbBar";
import TableActionsMenu from "../components/TableActionsMenu";
import NoData from "../components/NoData";
import ActionsBar from "../components/ActionsBar";
import friendlyDate from "../utilities/friendlyDate";
import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import axios from "axios";

export default () => {
    const [data, setData] = useState({ data: [] });

    useEffect(async () => {
        const result = await axios({
            method: "REPORT",
            url: "http://localhost:4073/api/v1/users/",
            data: {
                page_number: 1,
                page_length: 10
            }
        });

        setData(result.data);
        console.log(data);
    });

    const head = {
        cells: [
            {
                key: "username",
                content: "Username",
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
                isSortable: false
            },
            {
                key: "editedAt",
                content: "Edited At",
                isSortable: false
            },
            {
                key: "actions",
                content: null,
                isSortable: false,
                width: 15
            }
        ]
    };

    // TODO: add onClick to row for left and right-mouse buttons. Left = Edit, Right = Dropdown.
    const rows = data.data.map((row, i) => ({
        key: `${i}-row`,
        cells: row
            .map(([username, emailAddress, createdAt, editedAt]) => [
                username,
                emailAddress,
                friendlyDate(createdAt),
                friendlyDate(editedAt),
                <TableActionsMenu /> // TODO: this will accept a 'key' attribute (in this case username) so that it can use it for the edit and delete functions
            ])
            .map((cell, i) => ({
                key: `${i}-cell`,
                content: cell
            }))
    }));
    console.log(rows);

    return (
        <PageMargins>
            <PageHeader
                breadcrumbs={
                    <BreadcrumbBar
                        breadcrumbs={[
                            ["Home", "/"],
                            ["Users", ""]
                        ]}
                    />
                }
                actions={<ActionsBar />}
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
        </PageMargins>
    );
};
