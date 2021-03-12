import { useLocation } from "react-router-dom";

export default () => {
    const location = useLocation();

    return { location };
};
