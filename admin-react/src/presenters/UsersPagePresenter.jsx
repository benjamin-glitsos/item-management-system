import { useContext } from "react";
import ContentLayout from "%/presenters/ContentLayout";
import ListPresenter from "%/presenters/ListPresenter";
import { UsersContext } from "%/containers/UsersPageContainer";

export default () => {
    const context = useContext(UsersContext);
    return (
        <ContentLayout
            title={context.metaTitle}
            description={context.description}
        >
            <ListPresenter
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
        </ContentLayout>
    );
};
