import { titleCase } from "title-case";

export default () => {
    const keyField = "sku";

    const nameSingular = "item";

    const namePlural = "items";

    const breadcrumb = [titleCase(namePlural), `/${namePlural}`];

    return { keyField, nameSingular, namePlural, breadcrumb };
};
