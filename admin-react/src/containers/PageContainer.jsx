export default ({ title, slug, description }) => {
    const metaTitle = `${title} : ${process.env.PROJECT_ABBREV || "IMS"}`;

    const homeBreadcrumb = ["Home", "/"];

    return { title, slug, metaTitle, description, homeBreadcrumb };
};
