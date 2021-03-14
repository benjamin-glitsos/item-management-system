export default {
    serverUrl:
        process.env.REACT_APP_PROJECT_MODE === "production"
            ? `https://${process.env.REACT_APP_CONTROLLER_SUBDOMAIN}.${process.env.REACT_APP_PROJECT_DOMAIN}/rest/`
            : `http://localhost:${process.env.REACT_APP_CONTROLLER_PORT}/api/rest/`
};
