import { useQuery as useReactQuery } from "react-query";
import client from "%/utilities/client";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({
    method,
    path,
    body,
    clientOptions = {},
    queryOptions = {}
}) =>
    useReactQuery(path, () => client({ method, path, body, clientOptions }), {
        retry: false,
        ...queryOptions
    });
