import { Fragment } from "react";
import { Helmet } from "react-helmet";

export default ({ title, description, children }) => (
    <Fragment>
        <Helmet>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="shrink-to-fit=yes" />
        </Helmet>
        {children}
    </Fragment>
);
