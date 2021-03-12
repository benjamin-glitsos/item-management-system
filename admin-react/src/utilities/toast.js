import { toast } from "react-toastify";

export default e =>
    toast(`${e.title}: ${e.description}`, {
        type: toast.type.ERROR,
        position: "bottom-right"
    });
