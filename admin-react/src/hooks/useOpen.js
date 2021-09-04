import { useQuery } from "react-query";
import service from "%/utilities/service";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default ({ path }) => {
    const query = useQuery(
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

    const data = {
        action: "open"
    };

    return {
        ...query,
        data: {
            openResponse: query.data,
            openData: data
        }
    };
};
