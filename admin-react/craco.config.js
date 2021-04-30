const path = require("path");
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
            "%": path.resolve(__dirname, "src/")
        }
    },
    eslint: {
        enable: false
    },
    babel: {
        plugins: ["@babel/plugin-proposal-optional-chaining", "ramda"]
    }
};
