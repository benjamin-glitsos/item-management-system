import axios from "axios";
import config from "%/config";

const service = (method, path, body) =>
    axios({
        method,
        url: config.serverUrl + `v1/${path}/`,
        data: body
    });

export const openService = ({ path }) => service("GET", path, null);

export const listService = ({ path, params }) =>
    service("REPORT", path, params);

export const createService = ({ path, body }) => service("POST", path, body);

export const editService = ({ path, body }) => service("PATCH", path, body);

export const deleteService = ({ path, method, keys }) =>
    service("DELETE", path, {
        method,
        [keyColumnPlural]: keys
    });
