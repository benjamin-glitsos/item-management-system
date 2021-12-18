import { createContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import { titleCase } from "title-case";
import readme from "%/assets/README.md";
import PageContainer from "%/components/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import GithubButton from "%/components/GithubButton";
import ImagesLightboxComponent from "%/components/ImagesLightbox";

export const Context = createContext();

const { Provider } = Context;

export default () => {
    const nameSingular = "readme";
    const namePlural = nameSingular;
    const title = titleCase(namePlural);
    const slug = nameSingular;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const pageContext = { ...pageContainer };

    return (
        <Provider value={pageContext}>
            <PageLayout
                title={pageContext.metaTitle}
                description={pageContext.description}
            >
                <ArticleLayout
                    title={pageContext.title}
                    breadcrumbs={[pageContext.homeBreadcrumb]}
                >
                    <ReactMarkdown source={readme} />
                    <h2>Codebase</h2>
                    <GithubButton />
                    <h2>Data Validation BPMN Diagram</h2>
                    <ImagesLightbox
                        images={[
                            {
                                src:
                                    process.env.PUBLIC_URL +
                                    "/images/data-validation-diagram.svg",
                                alt: `BPMN diagram of the data validation paths of the ${process.env.PROJECT_ABBREV}`
                            }
                        ]}
                    />
                    <h2>Database ER Diagram</h2>
                    <ImagesLightbox
                        images={[
                            {
                                src:
                                    process.env.PUBLIC_URL +
                                    "/images/er-diagram.svg",
                                alt: `Entity-Relationship diagram of the database of the ${process.env.PROJECT_ABBREV}`
                            }
                        ]}
                    />
                </ArticleLayout>
            </PageLayout>
        </Provider>
    );
};

const ImagesLightbox = styled(ImagesLightboxComponent)`
    margin-top: 28px;
    img {
        max-width: 100%;
    }
`;
