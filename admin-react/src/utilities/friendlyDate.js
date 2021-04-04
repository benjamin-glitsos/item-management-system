import dateFormat from "dateformat";
import TimeAgo from "javascript-time-ago";
import en from "javascript-time-ago/locale/en";
import Tooltip from "@atlaskit/tooltip";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";

TimeAgo.addDefaultLocale(en);
const timeAgo = new TimeAgo("en-US");

export default dateTimeStr => {
    if (dateTimeStr === null) {
        return "Never";
    } else {
        const now = new Date();
        const date = new Date(dateTimeStr);
        const oneDay = 1000 * 60 * 60 * 24;
        const isLessThanOneDayAgo = date > now - oneDay;
        const fullDate = dateFormat(now, `mmmm dS, yyyy "at" h:MMtt`);
        if (isLessThanOneDayAgo) {
            return (
                <Tooltip content={fullDate}>
                    {capitaliseFirstLetter(timeAgo.format(date))}
                </Tooltip>
            );
        } else {
            return fullDate;
        }
    }
};
