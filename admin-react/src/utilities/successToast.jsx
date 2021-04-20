import toast from "%/utilities/toast";
import Message from "%/utilities/message";

export default () => {
    const message = new Message(
        "success",
        "Success",
        "Successfully completed."
    );
    console.log(message);
    toast(message);
};
