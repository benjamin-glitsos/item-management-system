import { Fragment } from "react";
import { Helmet } from "react-helmet";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import Content from "%/components/Content";

export default ({ title, description, breadcrumbs, maxWidth, children }) => (
    <Fragment>
        <Helmet>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="shrink-to-fit=yes" />
        </Helmet>
        <Content maxWidth={maxWidth}>
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            >
                {title}
            </PageHeader>
            {children}
        </Content>
    </Fragment>
);
