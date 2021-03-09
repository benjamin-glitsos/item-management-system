import capitalise from "./capitalise";
import dateFormat from "dateformat";
import timeAgo from "node-time-ago";

export default (dateTimeStr = "Never") => {
    const now = new Date();
    const date = new Date(dateTimeStr);
    const oneDay = 1000 * 60 * 60 * 24;
    const isLessThanOneDayAgo = date > now - oneDay;
    if (isLessThanOneDayAgo) {
        return capitalise(timeAgo(date));
    } else {
        return dateFormat(now, `mmmm dS, yyyy "at" h:MMtt`);
    }
};
