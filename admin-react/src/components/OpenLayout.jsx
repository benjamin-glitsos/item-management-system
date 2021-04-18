import { Fragment } from "react";
import { Helmet } from "react-helmet";
import ArticleLayout from "%/components/ArticleLayout";

export default ({ title, description, children }) => (
    <ArticleLayout title={title} description={description} maxWidth="1200px">
        {children}
    </ArticleLayout>
);
