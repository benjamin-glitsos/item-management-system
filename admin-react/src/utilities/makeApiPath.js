import config from "%/config";

export default path => `${config.serverUrl}/v1/${path}`;
