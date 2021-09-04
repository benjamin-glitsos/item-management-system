import { useQuery } from "react-query";
import service from "%/utilities/service";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({
    id,
    method,
    path,
    body,
    axiosOptions = {},
    queryOptions = {}
}) =>
    useQueryService(id, () => service({ method, path, body, axiosOptions }), {
        retry: false,
        ...queryOptions
    });
