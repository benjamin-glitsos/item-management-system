import { useContext } from "react";
import styled from "styled-components";
import ReactMarkdown from "react-markdown";
import { titleCase } from "title-case";
import readme from "%/assets/README.md";
import PageContainer from "%/components/Page/PageContainer";
import PageLayout from "%/components/PageLayout";
import ArticleLayout from "%/components/ArticleLayout";
import GithubButton from "%/components/GithubButton";
import ImagesLightbox from "%/components/ImagesLightbox";
import { ReadmeContext } from "%/components/ReadmePage";

export default () => {
    const context = useContext(ReadmeContext);

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
