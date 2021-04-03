const path = require(`path`);
const CracoRawLoaderPlugin = require("@baristalabs/craco-raw-loader");
const TerserPlugin = require("terser-webpack-plugin");

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
        },
        configure: {
            optimization: {
                minimize: true,
                minimizer: [new TerserPlugin()]
            }
        }
    },
    eslint: {
        enable: false
    },
    babel: {
        plugins: ["@babel/plugin-proposal-optional-chaining"]
    }
};
