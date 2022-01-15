export default ({ nameSingular, namePlural, title, slug, description }) => {
    const metaTitle = `${title} : ${process.env.PROJECT_ABBREV || "IMS"}`;

    const homeBreadcrumb = ["Home", "/"];

    return {
        nameSingular,
        namePlural,
        title,
        slug,
        metaTitle,
        description,
        homeBreadcrumb
    };
};
