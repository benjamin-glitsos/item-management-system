import { Fragment, useContext } from "react";
import { Helmet } from "react-helmet";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import Content from "%/components/Content";

export default ({ context, children }) => {
    const cx = useContext(context);

    return (
        <Fragment>
            <Helmet>
                <title>{cx.page.tabTitle}</title>
                <meta name="description" content={cx.page.pageDescription} />
                <meta name="viewport" content="shrink-to-fit=yes" />
            </Helmet>
            <Content maxWidth={cx.edit.maxWidth}>
                <PageHeader
                    breadcrumbs={<BreadcrumbBar breadcrumbs={cx.breadcrumbs} />}
                >
                    {cx.page.pageTitle}
                </PageHeader>
                {children}
            </Content>
        </Fragment>
    );
};
