import dateFormat from "dateformat";
import timeAgo from "node-time-ago";
import Tooltip from "@atlaskit/tooltip";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";

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
                <Tooltip content="wow">
                    {capitaliseFirstLetter(timeAgo(date))}
                </Tooltip>
            );
        } else {
            return fullDate;
        }
    }
};
