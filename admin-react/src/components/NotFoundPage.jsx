import { createContext, useContext } from "react";
import { useHistory, useLocation } from "react-router-dom";
import styled from "styled-components";
import { titleCase } from "title-case";
import Button from "@atlaskit/button";
import PageContainer from "%/components/Page/PageContainer";
import ListContainer from "%/components/List/ListContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const context = useContext(Context);

    const nameSingular = "page not found";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description:
            "The page at this location does not exist. Please click on the button below to return to the homepage."
    });

    const pageContext = { ...pageContainer };

    const location = useLocation();
    const breadcrumbs = [
        pageContext.homeBreadcrumb,
        ...location.pathname
            .split("/")
            .filter(s => s !== "")
            .map(s => [s, s])
    ];

    const history = useHistory();
    const handleReturnHome = () => {
        history.push("/");
    };

    return (
        <Provider value={pageContext}>
            <PageLayout
                title={pageContext.metaTitle}
                description={pageContext.description}
            >
                <ArticleLayout
                    title={pageContext.title}
                    breadcrumbs={breadcrumbs}
                >
                    <p>{pageContext.description}</p>
                    <HomeButtonStyles>
                        <Button appearance="primary" onClick={handleReturnHome}>
                            Back to Home
                        </Button>
                    </HomeButtonStyles>
                </ArticleLayout>
            </PageLayout>
        </Provider>
    );
};

const HomeButtonStyles = styled.div`
    margin-top: 28px;
`;
