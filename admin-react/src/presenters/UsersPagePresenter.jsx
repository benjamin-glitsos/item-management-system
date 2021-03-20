import PageLayout from "%/presenters/PageLayout";
import ListLayout from "%/presenters/ListLayout";

export default ({
    slug,
    title,
    metaTitle,
    description,
    homeBreadcrumb,
    doesDataExist,
    head,
    rows,
    state,
    setPageNumber,
    setPageLength,
    deleteItemsAction
}) => (
    <PageLayout title={metaTitle} description={description}>
        <ListLayout
            title={title}
            doesDataExist={doesDataExist}
            head={head}
            rows={rows}
            state={state}
            setPageNumber={setPageNumber}
            setPageLength={setPageLength}
            deleteItemsAction={deleteItemsAction}
            breadcrumbs={[homeBreadcrumb, [title, `/${slug}`]]}
        />
    </PageLayout>
);
