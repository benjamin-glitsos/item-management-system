import { toast } from "react-toastify";

export default error =>
    toast.error(`${error.title}: ${error.description}`, {
        position: "bottom-right"
    });
