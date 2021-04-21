import toast from "%/utilities/toast";
import Message from "%/utilities/message";

export default () => {
    const message = new Message(
        "no_new_data_to_submit",
        "No new data to submit",
        "No values in the form are different to the data that is already present on the server."
    );
    console.log(message);
    toast(message);
};
