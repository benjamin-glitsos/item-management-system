import { Fragment } from "react";
import { Helmet } from "react-helmet";
import Content from "%/components/Content";

export default ({ title, description, maxWidth, children }) => (
    <Fragment>
        <Helmet>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="shrink-to-fit=yes" />
        </Helmet>
        <Content maxWidth={maxWidth}>{children}</Content>
    </Fragment>
);
