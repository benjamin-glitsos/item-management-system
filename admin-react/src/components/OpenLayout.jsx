import ContentLayout from "%/components/ContentLayout";

export default ({ title, breadcrumbs, isLoading, children }) => (
    <ContentLayout
        title={!isLoading ? title : ""}
        breadcrumbs={!isLoading ? breadcrumbs : []}
        maxWidth={"1200px"}
    >
        {children}
    </ContentLayout>
);
