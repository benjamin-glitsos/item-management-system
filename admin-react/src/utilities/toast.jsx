import { toast } from "react-toastify";
import Toast from "%/components/Toast";

export default ({ title, description }) => {
    toast(<Toast title={title} description={description} />);
};
