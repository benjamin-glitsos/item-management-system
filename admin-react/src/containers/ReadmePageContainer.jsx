import PageContainer from "%/containers/PageContainer";

export default () =>
    PageContainer({
        title: "Readme",
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });
