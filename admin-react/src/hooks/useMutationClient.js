import { useMutation } from "react-query";
import client from "%/utilities/client";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ method, path, body }) =>
    useMutation(
        path,
        () =>
            axios({
                method,
                url: path,
                data: body
            }),
        {
            retry: false
        }
    );
