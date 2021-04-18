import R from "ramda";
import { titleCase } from "title-case";
import PageContainer from "%/components/Page/PageContainer";
import ListContainer from "%/components/List/ListContainer";
import PageLayout from "%/components/PageLayout";
import List from "%/components/List/List";
import formatNull from "%/utilities/formatNull";

export default () => {
    const nameSingular = "item";
    const namePlural = "items";
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
        headContentColumns: [
            { key: "key", content: "Key", isSortable: true },
            { key: "name", content: "Name", isSortable: true },
            {
                key: "description",
                content: "Description",
                isSortable: true
            }
        ],
        rowTransform: R.evolve({ description: formatNull }),
        keyColumnSingular: "key",
        keyColumnPlural: "keys"
    });

    const pageContext = {
        nameSingular,
        namePlural,
        title,
        ...pageContainer,
        ...listContainer
    };

    return (
        <PageLayout
            title={pageContext.metaTitle}
            description={pageContext.description}
        >
            <List context={pageContext} />
        </PageLayout>
    );
};
