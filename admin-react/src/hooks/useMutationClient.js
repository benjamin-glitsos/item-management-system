import axios from "axios";
import { useMutation } from "react-query";
import makeApiPath from "utilities/makeApiPath";

export default (method, path, clientOptions = {}, queryOptions = {}) =>
    useMutation(
        body =>
            axios.request({
                method,
                url: makeApiPath(path),
                data: body,
                ...clientOptions
            }),
        {
            mutationKey: `${method} ${path}`,
            retry: false,
            ...queryOptions
        }
    );
