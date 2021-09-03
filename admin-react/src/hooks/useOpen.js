import { useQuery } from "react-query";
import service from "%/utilities/service";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path }) =>
    useQuery(
        ["open", ...path],
        () => service({ method: "GET", path, body: null }),
        {
            retry: false,
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            }
        }
    );
