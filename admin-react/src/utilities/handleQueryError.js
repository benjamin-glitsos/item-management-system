import R from "ramda";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

const handleQueryError = R.curryN(1, error => {
    const responseErrors = error?.response?.data?.errors;
    if (responseErrors) {
        for (const error of responseErrors) {
            console.error(error);
            const { title, description } = error;
            toast({ title, description });
        }
    } else {
        console.error(error);
        unspecifiedErrorToast();
    }
});

export default handleQueryError;
