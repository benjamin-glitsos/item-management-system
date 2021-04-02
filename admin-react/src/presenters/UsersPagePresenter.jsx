import { useContext } from "react";
import PageLayout from "%/presenters/PageLayout";
import ListPresenter from "%/presenters/ListPresenter";
import List from "%/components/List";
import { UsersContext } from "%/components/UsersPage";

export default () => {
    const context = useContext(UsersContext);
    return (
        <PageLayout title={context.metaTitle} description={context.description}>
            <List context={context} />
        </PageLayout>
    );
};
