import toast from "%/utilities/toast";
import Message from "%/utilities/message";

export default () => {
    const error = new Message("error", "Error", "Something went wrong.");
    console.error(error);
    toast(error);
};
