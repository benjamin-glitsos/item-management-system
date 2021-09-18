import R from "ramda";
import axios from "axios";
import { useMutation } from "react-query";
import handleQueryError from "%/utilities/handleQueryError";
import makeApiPath from "%/utilities/makeApiPath";
import successToast from "%/utilities/successToast";
import joinPath from "%/utilities/joinPath";
import noNewDataToSubmitToast from "%/utilities/noNewDataToSubmitToast";

export default ({
    method,
    path,
    context: { namePlural, keyField, history, originalData },
    onSuccess,
    clientConfig = {},
    queryConfig = {}
}) =>
    useMutation(
        async body => {
            if (R.isEmpty(body)) {
                noNewDataToSubmitToast();
                return {};
            } else {
                return await axios({
                    method,
                    url: makeApiPath(path),
                    data: body,
                    ...clientConfig
                });
            }
        },
        {
            mutationKey: path,
            onError: handleQueryError,
            onSuccess,
            ...queryConfig
        }
    );
