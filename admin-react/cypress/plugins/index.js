/**
 * @type {Cypress.PluginConfig}
 */
// eslint-disable-next-line no-unused-vars

require("dotenv").config({ path: "../../../.env" });

module.exports = (on, config) => {
    config.baseUrl = `http://localhost:${process.env.ADMIN_PORT}`;
    config.env = Object.assign(config.env, process.env);
    config.env.API_BASE_URL = `http://localhost:${process.env.CONTROLLER_PORT}`;

    return config;
};
