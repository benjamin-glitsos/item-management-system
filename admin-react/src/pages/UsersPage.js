import PageMargins from "../components/PageMargins";
import DynamicTable from "@atlaskit/dynamic-table";
import DropdownMenu, {
    DropdownItem,
    DropdownItemGroup
} from "@atlaskit/dropdown-menu";
import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import PageHeader from "@atlaskit/page-header";
import ButtonGroup from "@atlaskit/button/button-group";
import Button from "@atlaskit/button/standard-button";

export default () => {
    const breadcrumbs = (
        <Breadcrumbs onExpand={() => {}}>
            <BreadcrumbsItem text="Some project" key="Some project" />
            <BreadcrumbsItem text="Parent page" key="Parent page" />
        </Breadcrumbs>
    );

    const actionsBar = (
        <ButtonGroup>
            <Button appearance="primary">Create New</Button>
            <Button>Delete</Button>
        </ButtonGroup>
    );

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
        <PageMargins>
            <PageHeader breadcrumbs={breadcrumbs} actions={actionsBar}>
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
                onSetPage={() => console.log("onSetPage")}
            />
        </PageMargins>
    );
};
