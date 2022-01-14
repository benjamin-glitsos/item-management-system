import { useMutation as useReactQueryMutation } from "react-query";
import axios from "axios";
import makeApiPath from "utilities/makeApiPath";

export default useReactQueryMutation(
    (method, path, body, clientOptions = {}, mutationOptions = {}) =>
        axios.request({
            url: makeApiPath(path),
            method,
            data: body,
            ...clientOptions
        }),
    {
        retry: false,
        ...mutationOptions
    }
);
