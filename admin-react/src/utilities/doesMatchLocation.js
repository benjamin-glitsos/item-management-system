import { useLocation } from "react-router-dom";

const location = useLocation();

export default path => matchPath(location.pathname, { path, exact: true });
