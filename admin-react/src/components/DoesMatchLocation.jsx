import { useLocation } from "react-router-dom";

export default () => {
    const location = useLocation();

    matchPath(location.pathname, { path, exact: true });
};
