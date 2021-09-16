import { useMutation } from "react-query";
import client from "%/utilities/client";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path, body }) =>
    useMutation(path, () => axios.get(path, body), {
        // TODO: make it accept method, instead of using get method
        retry: false
    });
