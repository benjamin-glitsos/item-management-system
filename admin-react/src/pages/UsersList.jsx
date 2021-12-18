import { titleCase } from "title-case";
import PageContainer from "%/components/PageContainer";
import ListContainer from "%/components/ListContainer";
import List from "%/components/List";
import config from "%/config";

export default () => {
    const [nameSingular, namePlural] = config.names.users;
    const keyColumnSingular = "username";
    const keyColumnPlural = "usernames";
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A list of ${namePlural} who can log into the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const listContainer = ListContainer({
        headContentColumns: [
            { key: "username", content: "Username", isSortable: true },
            { key: "first_name", content: "Name", isSortable: true },
            {
                key: "email_address",
                content: "Email Address",
                isSortable: true
            }
        ],
        nameSingular,
        namePlural,
        keyColumnSingular,
        keyColumnPlural
    });

    const pageContext = {
        ...pageContainer,
        ...listContainer
    };

    return <List context={pageContext} />;
};
