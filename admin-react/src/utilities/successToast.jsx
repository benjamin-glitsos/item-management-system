import toast from "utilities/toast";
import Message from "utilities/message";

export default props => {
    const message = new Message(
        "success",
        props?.title || "Success",
        props?.description || "Successfully completed."
    );
    toast(message);
};
