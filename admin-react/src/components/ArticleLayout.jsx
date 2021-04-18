import { Fragment } from "react";
import { Helmet } from "react-helmet";
import ContentLayout from "%/components/ContentLayout";

export default ({ title, breadcrumbs, children }) => (
    <ContentLayout title={title} breadcrumbs={breadcrumbs} maxWidth="800px">
        {children}
    </ContentLayout>
);
