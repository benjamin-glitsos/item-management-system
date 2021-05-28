import ContentLayout from "%/components/ContentLayout";

export default ({ title, breadcrumbs, actions, isLoading, children }) => (
    <ContentLayout
        title={!isLoading ? title : ""}
        breadcrumbs={!isLoading ? breadcrumbs : []}
        actions={actions}
        maxWidth={"1200px"}
    >
        {children}
    </ContentLayout>
);
