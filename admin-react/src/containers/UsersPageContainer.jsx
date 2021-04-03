import transform from "transform-object";
import { Checkbox } from "@atlaskit/checkbox";
import PageContainer from "%/containers/PageContainer";
import ListContainer from "%/containers/ListContainer";
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
        headContent: [
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
            }
        ],
        rowTransform: row =>
            transform(row, {
                created_at: friendlyDate,
                edited_at: d => friendlyDate(fromMaybe(d))
            })
    });

    return { title, slug, ...pageContainer, ...listContainer };
};
