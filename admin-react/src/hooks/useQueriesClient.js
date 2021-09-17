import axios from "axios";
import { useQueries } from "react-query";
import toast from "%/utilities/toast";
import handleQueryError from "%/utilities/handleQueryError";
import makeApiPath from "%/utilities/makeApiPath";

export default ({ paths, config }) =>
    useQueries(
        paths.map(path => ({
            queryKey: path,
            queryFn: () => axios.get(makeApiPath(path)),
            onError: handleQueryError,
            retry: false,
            ...config
        }))
    );
