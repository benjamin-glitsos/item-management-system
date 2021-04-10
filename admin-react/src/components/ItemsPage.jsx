import { createContext, useContext } from "react";
import { titleCase } from "title-case";
import PageContainer from "%/containers/PageContainer";
import ListContainer from "%/containers/ListContainer";
import PageLayout from "%/presenters/PageLayout";
import List from "%/components/List";
import fromMaybe from "%/utilities/fromMaybe";
import friendlyDate from "%/utilities/friendlyDate";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const context = useContext(Context);

    const nameSingular = "item";
    const namePlural = "items";
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A list of ${namePlural} who can log into the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const listContainer = ListContainer({
        apiPath: `v1/${slug}/`,
        defaultState: {
            request: {
                body: {}
            },
            response: {
                data: {
                    items: []
                },
                errors: []
            },
            isLoading: false,
            selected: []
        },
        headContentColumns: [
            { key: "key", content: "Key", isSortable: true },
            { key: "name", content: "Name", isSortable: true },
            {
                key: "description",
                content: "Description",
                isSortable: true
            }
        ]
    });

    const pageContext = {
        nameSingular,
        namePlural,
        title,
        ...pageContainer,
        ...listContainer
    };

    return (
        <Provider value={pageContext}>
            <PageLayout
                title={pageContext.metaTitle}
                description={pageContext.description}
            >
                <List context={pageContext} />
            </PageLayout>
        </Provider>
    );
};
