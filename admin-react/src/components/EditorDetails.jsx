import { Fragment } from "react";
import { Link } from "react-router-dom";
import formatDate from "%/utilities/formatDate";

export default ({ at, by }) =>
    at && by ? (
        <Fragment>
            {formatDate(at)}
            by <Link to={`/users/${by}`}>{by}</Link>
        </Fragment>
    ) : (
        formatDate()
    );
