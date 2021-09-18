import { Fragment, useContext } from "react";
import { Helmet } from "react-helmet";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import Content from "%/components/Content";

export default ({ context, children }) => {
    const cx = useContext(context);
    const title = cx.page.tabTitle;
    const description = cx.page.pageDescription;
    const breadcrumbs = cx.breadcrumbs;
    const maxWidth = cx.edit.maxWidth;

    return (
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
};
