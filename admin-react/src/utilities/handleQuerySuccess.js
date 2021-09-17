import R from "ramda";
import successToast from "%/utilities/successToast";

const handleQuerySuccess = R.curryN(1, success => {
    successToast();
});

export default handleQuerySuccess;
