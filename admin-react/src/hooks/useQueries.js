import { useQueries as useReactQueries } from "react-query";
import client from "%/utilities/client";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default queries =>
    useReactQueries(
        queries.map(
            ({
                method,
                path,
                body,
                clientOptions = {},
                queryOptions = {}
            }) => ({
                queryKey: path,
                queryFn: () => client({ method, path, body, clientOptions }),
                retry: false,
                ...queryOptions
            })
        )
    );
