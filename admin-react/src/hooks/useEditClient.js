import axios from "axios";
import { useMutation } from "react-query";
import toast from "%/utilities/toast";
import handleQueryError from "%/utilities/handleQueryError";
import makeApiPath from "%/utilities/makeApiPath";
import successToast from "%/utilities/successToast";
import joinPath from "%/utilities/joinPath";

export default ({
    method,
    path,
    context: { namePlural, keyField, history, originalData },
    clientConfig = {},
    queryConfig = {}
}) =>
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
            onSuccess: successData => {
                const data = successData.data.data[0];

                if (originalData[keyField] !== data[keyField]) {
                    history.replace(
                        "/" + joinPath([namePlural, data[keyField]])
                    );
                }

                successToast({
                    title: "Saved",
                    description: "Successfully edited"
                });
            },
            ...queryConfig
        }
    );
