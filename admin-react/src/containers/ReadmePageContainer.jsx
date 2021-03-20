export default () => {
    const title = "Readme";

    const description = `Information about the architecture and technology stack of the ${
        process.env.PROJECT_NAME || "Item Management System"
    }.`;

    return { title, description };
};
