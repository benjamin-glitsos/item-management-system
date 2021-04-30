/**
 * @type {Cypress.PluginConfig}
 */
// eslint-disable-next-line no-unused-vars
import dotenv from "dotenv";

dotenv.config({ path: ".." });

module.exports = (on, config) => {
    config.env = Object.assign(config.env, process.env);
    return config;
};
