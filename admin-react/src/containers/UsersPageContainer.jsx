import pipe from "pipe-functions";
import { Checkbox } from "@atlaskit/checkbox";
import PageContainer from "%/containers/PageContainer";
import ListContainer from "%/containers/ListContainer";
import RemoveAllSelected from "%/presenters/RemoveAllSelected";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";

export default () => {
    const title = "Users";
    const slug = "users";

    const pageContainer = PageContainer({
        title,
        slug,
        description: `A list of users who can log into the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const listContainer = ListContainer({
        apiPath: `v1/${slug}/`,
        defaultState: {
            request: {
                body: {}
            },
            response: {
                data: {
                    items: []
                },
                errors: []
            },
            isLoading: false,
            selected: []
        },
        head: (setRemoveAllSelected, selectedState) => ({
            cells: [
                {
                    key: "isSelected",
                    content: (
                        <RemoveAllSelected
                            doesSelectionExist={selectedState.length > 0}
                            action={setRemoveAllSelected}
                        />
                    ),
                    isSortable: false,
                    width: 3
                },
                { key: "username", content: "Username", isSortable: true },
                { key: "first_name", content: "Name", isSortable: true },
                {
                    key: "email_address",
                    content: "Email Address",
                    isSortable: true
                },
                {
                    key: "created_at",
                    content: "Created At",
                    isSortable: true,
                    width: 15
                },
                {
                    key: "edited_at",
                    content: "Edited At",
                    isSortable: true,
                    width: 15
                },
                { key: "actions", content: null, isSortable: false, width: 10 }
            ]
        }),
        rows: (row, i, selectedState, setSelected, TableActionsMenu) => ({
            key: `${title}/Table/Row/${i}`,
            cells: pipe(
                row,
                ({ username, email_address, name, created_at, edited_at }) => [
                    username,
                    name,
                    email_address,
                    friendlyDate(created_at),
                    friendlyDate(fromMaybe(edited_at))
                ],
                row => [
                    <Checkbox
                        isChecked={selectedState.includes(row[0])}
                        onChange={() => setSelected(row[0])}
                    />,
                    ...row,
                    <TableActionsMenu />
                ],
                x =>
                    x.map((x, i) => ({
                        key: `Table/Cell/${i}`,
                        content: x
                    }))
            )
        })
    });

    return { title, slug, ...pageContainer, ...listContainer };
};
