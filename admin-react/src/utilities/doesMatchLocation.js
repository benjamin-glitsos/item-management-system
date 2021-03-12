import { matchPath } from "react-router";

export default (path, location) =>
    matchPath(location.pathname, { path, exact: true });
