import PageMargins from "../components/PageMargins";
import BreadcrumbBar from "../components/BreadcrumbBar";
import TableActionsMenu from "../components/TableActionsMenu";
import ActionsBar from "../components/ActionsBar";
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
                    content: "Dole"
                },
                {
                    key: "4",
                    content: "Dole"
                },
                {
                    key: "5",
                    content: TableActionsMenu
                }
            ]
        }
    ];

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
                actions={ActionsBar}
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
                onSetPage={() => console.log("onSetPage")}
            />
        </PageMargins>
    );
};
