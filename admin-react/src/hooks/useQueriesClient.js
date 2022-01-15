import axios from "axios";
import { useQueries } from "react-query";
import unspecifiedErrorToast from "utilities/unspecifiedErrorToast";
import makeApiPath from "utilities/makeApiPath";

export default (paths, clientOptions = {}, queryOptions = {}) =>
    useQueries(
        paths.map(path => ({
            queryKey: path,
            queryFn: () => axios.get(makeApiPath(path), clientOptions),
            retry: false,
            refetchOnMount: false,
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            },
            enabled: false,
            ...queryOptions
        }))
    );
