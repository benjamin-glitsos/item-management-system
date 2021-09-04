import axios from "axios";
import config from "%/config";
import joinPath from "%/utilities/joinPath";

export default ({ method, path, body, options = {} }) =>
    axios({
        method,
        url: config.serverUrl + joinPath(["v1", ...path]),
        data: body,
        ...options
    });
