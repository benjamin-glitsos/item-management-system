import axios from "axios";
import { useMutation } from "react-query";
import toast from "%/utilities/toast";
import handleQueryError from "%/utilities/handleQueryError";
import handleQuerySuccess from "%/utilities/handleQuerySuccess";
import makeApiPath from "%/utilities/makeApiPath";

export default ({ method, path, clientConfig = {}, queryConfig = {} }) =>
    useMutation(
        async body =>
            await axios({
                method,
                url: makeApiPath(path),
                data: body,
                ...clientConfig
            }),
        {
            mutationKey: path,
            onError: handleQueryError,
            onSuccess: handleQuerySuccess,
            ...queryConfig
        }
    );
