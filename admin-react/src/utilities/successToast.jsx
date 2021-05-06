import toast from "%/utilities/toast";
import Message from "%/utilities/message";

export default ({ title, description }) => {
    const message = new Message(
        "success",
        title || "Success",
        description || "Successfully completed."
    );
    toast(message);
};
