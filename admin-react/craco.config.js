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
    ]
};
