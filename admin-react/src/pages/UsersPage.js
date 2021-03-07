import { useEffect, useState } from "react";
import PageMargins from "../components/PageMargins";
import BreadcrumbBar from "../components/BreadcrumbBar";
import TableActionsMenu from "../components/TableActionsMenu";
import TableStatusBar from "../components/TableStatusBar";
import NoData from "../components/NoData";
import ActionsBar from "../components/ActionsBar";
import friendlyDate from "../utilities/friendlyDate";
import fromMaybe from "../utilities/fromMaybe";
import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import { Checkbox } from "@atlaskit/checkbox";
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
        key: `UsersPage/Table/Row/${i}`,
        cells: (([username, emailAddress, createdAt, editedAt]) => [
            <Checkbox />,
            username,
            emailAddress,
            friendlyDate(createdAt),
            friendlyDate(fromMaybe(editedAt)),
            <TableActionsMenu />
        ])(row).map((cell, i) => ({
            key: `UsersPage/Table/Cell/${i}`,
            content: cell
        }))
    }));

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
                bottomBar={
                    <TableStatusBar
                        placeholder={
                            "A list of users who have access to login to this system."
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
        </PageMargins>
    );
};
