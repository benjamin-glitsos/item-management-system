import config from "%/config";
import joinPath from "%/utilities/joinPath";

export default path => config.serverUrl + joinPath(["v1", ...path])
