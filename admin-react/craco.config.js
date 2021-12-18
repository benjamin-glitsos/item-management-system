const path = require(`path`);
const CracoRawLoaderPlugin = require("@baristalabs/craco-raw-loader");

module.exports = {
    reactScriptsVersion: "react-scripts",
    plugins: [
        {
            plugin: CracoRawLoaderPlugin,
            options: {
                test: /\.md$/
            }
        }
    ],
    webpack: {
        alias: {
            "%": path.resolve(__dirname, "src/"),
            "assets": path.resolve(__dirname, "src/assets/"),
            "hooks": path.resolve(__dirname, "src/hooks/"),
            "pages": path.resolve(__dirname, "src/pages/"),
            "services": path.resolve(__dirname, "src/services/"),
            "utilities": path.resolve(__dirname, "src/utilities/"),
            "elements": path.resolve(__dirname, "src/components/elements/"),
            "modules": path.resolve(__dirname, "src/components/modules/"),
            "templates": path.resolve(__dirname, "src/components/templates/")
        }
    },
    eslint: {
        enable: false
    },
    babel: {
        plugins: ["@babel/plugin-proposal-optional-chaining", "ramda"]
    }
};
