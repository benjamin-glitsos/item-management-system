import { titleCase } from "title-case";

export default () => {
    const keyField = "username";

    const nameSingular = "user";

    const namePlural = "users";

    const breadcrumb = [titleCase(namePlural), `/${namePlural}`];

    return { keyField, nameSingular, namePlural, breadcrumb };
};
