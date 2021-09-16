import axios from "axios";
import { useQueries } from "react-query";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";
import makeApiPath from "%/utilities/makeApiPath";

export default paths =>
    useQueries(
        paths.map(path => ({
            queryKey: path,
            queryFn: () => axios.get(makeApiPath(path)),
            retry: false,
            onError: error => {
                console.error(error);
                unspecifiedErrorToast();
            }
        }))
    );