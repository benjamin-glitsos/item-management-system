import { useContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import readme from "%/assets/README.md";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import GithubButton from "%/presenters/GithubButton";
import ImagesLightbox from "%/presenters/ImagesLightbox";
import { ReadmeContext } from "%/components/ReadmePage";

export default () => {
    const context = useContext(ReadmeContext);
    return (
        <PageLayout title={context.metaTitle} description={context.description}>
            <ArticleLayout
                title={context.title}
                breadcrumbs={[context.homeBreadcrumb]}
            >
                <ReactMarkdown>{readme}</ReactMarkdown>
                <h2>Codebase</h2>
                <GithubButton />
                <h2>Database Schema</h2>
                <DatabaseSchemaStyles>
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
                </DatabaseSchemaStyles>
            </ArticleLayout>
        </PageLayout>
    );
};

const DatabaseSchemaStyles = styled.div`
    margin-top: 28px;
`;
