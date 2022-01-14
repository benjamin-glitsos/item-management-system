import dateFormat from "dateformat";
import TimeAgo from "javascript-time-ago";
import en from "javascript-time-ago/locale/en";
import Tooltip from "@atlaskit/tooltip";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";

TimeAgo.addDefaultLocale(en);

const timeAgo = new TimeAgo();

const Today = ({ date, formattedDate }) => (
    <Tooltip content={formattedDate}>
        {capitaliseFirstLetter(timeAgo.format(date, { future: false }))}
    </Tooltip>
);

export default (d = null, hasTime = true) => {
    if (d === undefined) {
        return d;
    } else if (d === null) {
        return "(Never)";
    } else {
        const now = new Date();
        const date = new Date(d);
        const oneDay = 1000 * 60 * 60 * 24;
        const isToday = date > now - oneDay;
        if (!hasTime) {
            const formattedDate = dateFormat(date, "mmmm dS, yyyy");
            if (isToday) {
                return <Today date={date} formattedDate={formattedDate} />;
            } else {
                return formattedDate;
            }
        } else {
            const formattedDate = dateFormat(now, 'mmmm dS, yyyy "at" h:MMtt');
            if (isToday) {
                return <Today date={date} formattedDate={formattedDate} />;
            } else {
                return formattedDate;
            }
        }
    }
};
