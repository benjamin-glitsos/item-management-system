import { useContext } from "react";
import PageLayout from "%/presenters/PageLayout";
import ListLayout from "%/presenters/ListLayout";
import { UsersContext } from "%/containers/UsersPageContainer";

export default () => {
    const context = useContext(UsersContext);
    return (
        <PageLayout title={context.metaTitle} description={context.description}>
            <ListLayout
                title={context.title}
                doesDataExist={context.doesDataExist}
                head={context.head}
                rows={context.rows}
                state={context.state}
                setPageNumber={context.setPageNumber}
                setPageLength={context.setPageLength}
                deleteItemsAction={context.deleteItemsAction}
                breadcrumbs={[
                    context.homeBreadcrumb,
                    [context.title, `/${context.slug}`]
                ]}
            />
        </PageLayout>
    );
};
