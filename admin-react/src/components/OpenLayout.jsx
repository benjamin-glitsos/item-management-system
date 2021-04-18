import { Fragment } from "react";
import { Helmet } from "react-helmet";
import ArticleLayout from "%/components/ArticleLayout";

export default ({ title, breadcrumbs, children }) => (
    <ArticleLayout title={title} breadcrumbs={breadcrumbs} maxWidth="1200px">
        {children}
    </ArticleLayout>
);
