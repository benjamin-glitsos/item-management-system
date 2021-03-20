import PageLayout from "%/presenters/PageLayout";
import ListLayout from "%/presenters/ListLayout";

export default ({
    slug,
    title,
    metaTitle,
    description,
    homeBreadcrumb,
    head,
    rows,
    state,
    setPageNumber,
    setPageLength,
    deleteUsersAction
}) => (
    <PageLayout title={metaTitle} description={description}>
        <ListLayout
            title={title}
            head={head}
            rows={rows}
            state={state}
            setPageNumber={setPageNumber}
            setPageLength={setPageLength}
            deleteUsersAction={deleteUsersAction}
            breadcrumbs={[homeBreadcrumb, [title, `/${slug}`]]}
        />
    </PageLayout>
);
