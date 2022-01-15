import { Fragment } from "react";
import { Link } from "react-router-dom";
import formatDate from "utilities/formatDate";

export default ({ at, by }) => {
    if (at && by) {
        return (
            <Fragment>
                {formatDate(at)} by <Link to={`/users/${by}`}>{by}</Link>
            </Fragment>
        );
    } else {
        return formatDate();
    }
};
