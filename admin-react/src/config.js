export default {
    serverUrl:
        process.env.REACT_APP_PROJECT_MODE === "production"
            ? `https://${process.env.REACT_APP_CONTROLLER_SUBDOMAIN}.${process.env.REACT_APP_PROJECT_DOMAIN}`
            : `http://localhost:${process.env.REACT_APP_CONTROLLER_PORT}/api`,
    pageLengths: [10, 25, 50, 100],
    defaultPageLength: 10,
    defaultSort: ["created_at", "DESC"],
    names: {
        users: ["user", "users"],
        items: ["item", "items"]
    }
};
