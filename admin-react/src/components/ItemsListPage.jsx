import R from "ramda";
import { titleCase } from "title-case";
import PageContainer from "%/components/Page/PageContainer";
import ListContainer from "%/components/List/ListContainer";
import List from "%/components/List/List";
import formatNull from "%/utilities/formatNull";
import config from "%/config";

export default () => {
    const [nameSingular, namePlural] = config.names.items;
    const keyColumnSingular = "sku";
    const keyColumnPlural = "skus";
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
            { key: "sku", content: "SKU", isSortable: true },
            { key: "name", content: "Name", isSortable: true },
            {
                key: "description",
                content: "Description",
                isSortable: true
            }
        ],
        nameSingular,
        namePlural,
        keyColumnSingular,
        keyColumnPlural,
        rowTransform: R.evolve({ description: formatNull })
    });

    const pageContext = {
        ...pageContainer,
        ...listContainer
    };

    return <List context={pageContext} />;
};
