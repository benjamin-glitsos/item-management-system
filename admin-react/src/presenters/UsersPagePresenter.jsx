import { useContext } from "react";
import ContentLayout from "%/presenters/ContentLayout";
import ListPresenter from "%/presenters/ListPresenter";
import List from "%/components/List";
import { UsersContext } from "%/components/UsersPage";

export default () => {
    const context = useContext(UsersContext);
    return (
        <ContentLayout
            title={context.metaTitle}
            description={context.description}
        >
            <List context={context} />
        </ContentLayout>
    );
};
