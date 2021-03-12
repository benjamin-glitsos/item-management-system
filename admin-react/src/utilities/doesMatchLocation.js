import { matchPath } from "react-router";

export default (location, path) =>
    matchPath(location.pathname, { path, exact: true });
