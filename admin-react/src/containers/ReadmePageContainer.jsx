import PageContainer from "%/containers/PageContainer";

export default () => {
    const title = "Readme";
    const slug = "";

    const pageContainer = PageContainer({
        title: "Readme",
        slug,
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });
    return { ...pageContainer };
};
