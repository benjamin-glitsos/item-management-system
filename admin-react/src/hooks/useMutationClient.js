import { useMutation } from "react-query";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

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
            ...queryConfig
        }
    );
