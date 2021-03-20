import pipe from "pipe-functions";
import { Checkbox } from "@atlaskit/checkbox";
import PageContainer from "%/containers/PageContainer";
import ListContainer from "%/containers/ListContainer";
import RemoveAllSelected from "%/presenters/RemoveAllSelected";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";
import friendlyName from "%/utilities/friendlyName";

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
                { key: "username", content: "Username", isSortable: false },
                { key: "name", content: "Name", isSortable: false },
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
                { key: "actions", content: null, isSortable: false, width: 10 }
            ]
        }),
        rows: (row, i, selectedState, setSelected, TableActionsMenu) => ({
            key: `${title}/Table/Row/${i}`,
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

    return {
        title,
        slug,
        ...pageContainer,
        ...listContainer
    };
};
