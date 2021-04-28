import ContentLayout from "%/components/ContentLayout";

export default ({ title, breadcrumbs, children }) => (
    <ContentLayout title={title} breadcrumbs={breadcrumbs} maxWidth={"800px"}>
        {children}
    </ContentLayout>
);
