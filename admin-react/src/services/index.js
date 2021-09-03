import axios from "axios";
import axiosErrorHandler from "%/utilities/axiosErrorHandler";

export function listService(apiUrl, body, callback) {
    axios({
        method: "REPORT",
        url: apiUrl,
        data: body
    })
        .then(callback)
        .catch(axiosErrorHandler);
}

export function deleteService(apiUrl, method, keys) {
    axios({
        method: "DELETE",
        url: apiUrl,
        data: {
            method,
            [keyColumnPlural]: keys
        }
    }).catch(axiosErrorHandler);
}
