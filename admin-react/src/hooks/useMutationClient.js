import { useMutation } from "react-query";
import toast from "%/utilities/toast";
import handleQueryError from "%/utilities/handleQueryError";

export default ({ method, path, body, clientConfig = {}, queryConfig = {} }) =>
    useMutation(
        path,
        () =>
            axios({
                method,
                url: path,
                data: body,
                ...clientConfig
            }),
        {
            retry: false,
            onError: handleQueryError,
            ...queryConfig
        }
    );
