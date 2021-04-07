import PageContainer from "%/containers/PageContainer";

export default () => {
    const nameSingular = "page not found";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description:
            "The page at this location does not exist. Please click on the button below to return to the homepage."
    });
    return { ...pageContainer };
};
