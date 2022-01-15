import toast from "utilities/toast";
import Message from "utilities/message";

export default props => {
    const message = new Message(
        "no_new_data_to_submit",
        props?.title || "No new data to submit",
        props?.description ||
            "No values in the form are different to the data that is already present on the server."
    );
    toast(message);
};
