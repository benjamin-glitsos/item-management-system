import axios from "axios";
import config from "%/config";

export default (method, path, body) =>
    axios({
        method,
        url: config.serverUrl + `v1/${path}/`,
        data: body
    });
