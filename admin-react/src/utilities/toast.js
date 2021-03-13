import { toast } from "react-toastify";

export default error =>
    toast(`${error.title}: ${error.description}`, {
        type: toast.type.ERROR,
        position: "bottom-right"
    });
