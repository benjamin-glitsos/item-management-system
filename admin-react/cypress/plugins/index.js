require("dotenv").config({ path: require("find-config")(".env") });

module.exports = (on, config) => {
    config.baseUrl = `http://localhost:${process.env.ADMIN_PORT}`;
    config.env = Object.assign(config.env, process.env);
    config.env.API_BASE_URL = `http://localhost:${process.env.CONTROLLER_PORT}`;

    return config;
};
