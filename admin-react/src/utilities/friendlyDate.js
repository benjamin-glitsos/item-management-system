import dateFormat from "dateformat";
import timeAgo from "node-time-ago";
import capitaliseFirstLetter from "@/utilities/capitaliseFirstLetter";

export default dateTimeStr => {
    if (dateTimeStr === null) {
        return "Never";
    } else {
        const now = new Date();
        const date = new Date(dateTimeStr);
        const oneDay = 1000 * 60 * 60 * 24;
        const isLessThanOneDayAgo = date > now - oneDay;
        if (isLessThanOneDayAgo) {
            return capitaliseFirstLetter(timeAgo(date));
        } else {
            return dateFormat(now, `mmmm dS, yyyy "at" h:MMtt`);
        }
    }
};
