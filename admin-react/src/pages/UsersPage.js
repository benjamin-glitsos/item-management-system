import React from "react";
import ContentWrapper from "../components/ContentWrapper";
import PageTitle from "../components/PageTitle";
import DynamicTable from "@atlaskit/dynamic-table";
import DropdownMenu, {
    DropdownItem,
    DropdownItemGroup
} from "@atlaskit/dropdown-menu";

export default function UsersPage(props) {
    const head = {
        cells: [
            {
                key: "firstName",
                content: "First Name",
                isSortable: false
            },
            {
                key: "lastName",
                content: "Last Name",
                isSortable: false
            },
            {
                key: "actions",
                content: null,
                isSortable: false
            }
        ]
    };

    const rows = [
        {
            key: "1",
            cells: [
                {
                    key: "1",
                    content: "Bob"
                },
                {
                    key: "2",
                    content: "Dole"
                },
                {
                    key: "3",
                    content: (
                        <DropdownMenu trigger="Actions" triggerType="button">
                            <DropdownItemGroup>
                                <DropdownItem>Edit</DropdownItem>
                                <DropdownItem>Share</DropdownItem>
                                <DropdownItem>Move</DropdownItem>
                                <DropdownItem>Clone</DropdownItem>
                                <DropdownItem>Delete</DropdownItem>
                                <DropdownItem>Report</DropdownItem>
                            </DropdownItemGroup>
                        </DropdownMenu>
                    )
                }
            ]
        }
    ];
    return (
        <ContentWrapper>
            <PageTitle>Users</PageTitle>
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
                onSetPage={() => console.log("onSetPage")}
            />
        </ContentWrapper>
    );
}
