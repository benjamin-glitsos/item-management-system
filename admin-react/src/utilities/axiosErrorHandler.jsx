import { toast } from "react-toastify";

export default error => {
    const responseErrors = error?.response?.data?.errors;
    if (responseErrors) {
        for (const error of responseErrors) {
            console.error(error);
            toast("Error");
        }
    } else {
        console.error(error);
        toast("Something went wrong");
    }
};
