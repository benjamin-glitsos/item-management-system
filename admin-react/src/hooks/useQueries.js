import axios from "axios";
import { useQueries as useReactQueries } from "react-query";
import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";
import makeApiPath from "%/utilities/makeApiPath";

export default paths =>
    useReactQueries(
        paths.map(path => ({
            queryKey: path,
            queryFn: () => axios.get(makeApiPath(path))
            },
            retry: false,
            queryOptions: {
                onError: error => {
                    console.error(error);
                    unspecifiedErrorToast();
                }
            }
        }))
    );
