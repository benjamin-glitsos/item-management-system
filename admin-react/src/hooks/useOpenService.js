import useService from "%/hooks/useService";
import service from "%/utilities/service";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path }) =>
    useService({
        id: ["open", ...path],
        method: "GET",
        path,
        body: null,
        queryOptions: {
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            }
        }
    });
