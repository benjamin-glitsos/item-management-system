import useMutationfrom "%/hooks/useMutation";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path, body }) =>
    useMutation({
        method: "PATCH",
        path,
        body,
        queryOptions: {
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            }
        }
    });
