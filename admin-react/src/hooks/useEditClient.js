import useMutationClient from "%/hooks/useMutationClient";
import successToast from "%/utilities/successToast";

export default ({
    path,
    context: { namePlural, keyField, history, originalData },
    clientConfig = {},
    queryConfig = {}
}) =>
    useMutationClient({
        method: "PATCH",
        path,
        context: { namePlural, keyField, history, originalData },
        onSuccess: successData => {
            const data = successData?.data?.data?.[0];

            if (originalData[keyField] !== data[keyField]) {
                history.replace("/" + joinPath([namePlural, data[keyField]]));
            }

            successToast({
                title: "Saved",
                description: "Successfully edited"
            });
        },
        clientConfig,
        queryConfig
    });
