import PageContainer from "%/containers/PageContainer";

export default () => {
    const title = "Page not found";
    const slug = "";

    const pageContainer = PageContainer({
        title,
        slug,
        description:
            "The page at this location does not exist. Please click on the button below to return to the homepage."
    });
    return { ...pageContainer };
};
