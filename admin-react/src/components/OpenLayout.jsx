import ContentLayout from "%/components/ContentLayout";

export default ({ title, breadcrumbs, children }) => (
    <ContentLayout title={title} breadcrumbs={breadcrumbs} maxWidth={"1200px"}>
        {children}
    </ContentLayout>
);
