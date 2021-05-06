import toast from "%/utilities/toast";
import Message from "%/utilities/message";

export default props => {
    const error = new Message(
        "error",
        props?.title || "Error",
        props?.description || "Something went wrong."
    );
    console.error(error);
    toast(error);
};
