import { useQuery } from "react-query";
import service from "%/utilities/service";

export default ({ path }) =>
    useQuery(["open", path], () => service("GET", path, null));
