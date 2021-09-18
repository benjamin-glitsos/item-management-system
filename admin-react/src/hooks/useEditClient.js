import useMutationClient from "%/hooks/useMutationClient";
import successToast from "%/utilities/successToast";
import setFormValues from "%/utilities/setFormValues";

export default ({
    path,
    context: { namePlural, keyField, history, originalData, setValue },
    clientConfig = {},
    queryConfig = {}
}) =>
    useMutationClient({
        method: "PATCH",
        path,
        context: { namePlural, keyField, history, originalData },
        onSuccess: successData => {
            const maybeData = successData?.data?.data?.[0];
            if (maybeData) {
                if (originalData[keyField] !== maybeData[keyField]) {
                    history.replace(
                        "/" + joinPath([namePlural, maybeData[keyField]])
                    );
                }

                setFormValues(setValue, maybeData);

                successToast({
                    title: "Saved",
                    description: "Successfully edited"
                });
            }
        },
        clientConfig,
        queryConfig
    });
