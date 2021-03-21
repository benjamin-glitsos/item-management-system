import { useContext } from "react";
import ReactMarkdown from "react-markdown";
import readme from "%/assets/README.md";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import GithubButton from "%/presenters/GithubButton";
import { ReadmeContext } from "%/components/ReadmePage";

export default () => {
    const context = useContext(ReadmeContext);
    return (
        <PageLayout
            title={context.metaTitle}
            description={context.description}
        >
            <ArticleLayout
                title={context.title}
                breadcrumbs={[context.homeBreadcrumb]}
            >
                <ReactMarkdown>{context.readme}</ReactMarkdown>
                <GithubButton />
            </ArticleLayout>
        </PageLayout>
    );
};
