import capitalise from "./capitalise";
import dateFormat from "dateformat";
import timeAgo from "node-time-ago";

export default dateTimeStr => {
    const now = new Date();
    const date = new Date(dateTimeStr);
    const oneDay = 1000 * 60 * 60 * 24;
    const isLessThanOneDayAgo = date > now - oneDay;
    if (isLessThanOneDayAgo) {
        capitalise(timeAgo(date));
    } else {
        dateFormat(date, "mmmm dS, yyyy at h:MM:ss TT");
    }
};
