import useQuery from "%/hooks/useQuery";
import service from "%/utilities/service";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path }) =>
    useQuery({
        path,
        method: "GET",
        body: null,
        queryOptions: {
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            }
        }
    });
