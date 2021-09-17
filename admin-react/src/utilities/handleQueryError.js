import R from "ramda";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

const handleQueryError = R.curryN(1, error => {
    console.error(error);
    unspecifiedErrorToast();
});

export default handleQueryError;
