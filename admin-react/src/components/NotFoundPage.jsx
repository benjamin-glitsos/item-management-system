import { createContext } from "react";
import { useHistory, useLocation } from "react-router-dom";
import styled from "styled-components";
import Button from "@atlaskit/button";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import sentenceCase from "%/utilities/sentenceCase";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const nameSingular = "page not found";
    const namePlural = nameSingular;
    const title = sentenceCase(namePlural);
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
                    breadcrumbs={[
                        pageContext.homeBreadcrumb,
                        ["404 Page", slug]
                    ]}
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
