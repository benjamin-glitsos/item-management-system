import { createContext } from "react";
import { useHistory, useLocation } from "react-router-dom";
import styled from "styled-components";
import { titleCase } from "title-case";
import Button from "@atlaskit/button";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import generateBreadcrumbs from "%/utilities/generateBreadcrumbs";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const nameSingular = "page not found";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description: "The page at this location does not exist."
    });

    const pageContext = { ...pageContainer };

    const location = useLocation();

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
                    breadcrumbs={generateBreadcrumbs(
                        pageContext.homeBreadcrumb
                    )}
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
