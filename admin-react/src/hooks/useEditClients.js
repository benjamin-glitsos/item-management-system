import useQueries from "%/hooks/useQueries";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ paths }) =>
    useQueries(
        paths.map(path => ({
            path,
            method: "GET",
            body: null,
            queryOptions: {
                onError: error => {
                    console.error(error);
                    unspecifiedErrorToast();
                }
            }
        }))
    );
