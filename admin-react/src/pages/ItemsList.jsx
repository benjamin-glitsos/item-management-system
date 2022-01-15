import R from "ramda";
import { titleCase } from "title-case";
import PageContainer from "%/components/PageContainer";
import ListContainer from "%/components/ListContainer";
import List from "%/components/List";
import formatNull from "%/utilities/formatNull";
import formatDate from "%/utilities/formatDate";
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
            },
            {
                key: "acquisition_date",
                content: "Acquired",
                isSortable: true
            }
        ],
        nameSingular,
        namePlural,
        keyColumnSingular,
        keyColumnPlural,
        rowTransform: R.evolve({
            description: formatNull,
            acquisition_date: d => formatDate(d, false)
        })
    });

    const pageContext = {
        ...pageContainer,
        ...listContainer
    };

    return <List context={pageContext} />;
};
