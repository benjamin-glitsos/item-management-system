import toast from "%/utilities/toast";
import unspecifiedErrorToast from "%/utilities/unspecifiedErrorToast";

export default error => {
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
};
