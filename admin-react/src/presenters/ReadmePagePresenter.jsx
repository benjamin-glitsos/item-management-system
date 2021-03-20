import { useContext } from "react";
import ReactMarkdown from "react-markdown";
import readme from "%/assets/README.md";
import ContentLayout from "%/presenters/ContentLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import GithubButton from "%/presenters/GithubButton";
import { ReadmeContext } from "%/containers/ReadmePageContainer";

export default () => {
    const context = useContext(ReadmeContext);
    return (
        <ContentLayout title={context.title} description={context.description}>
            <ArticleLayout
                title={context.title}
                breadcrumbs={[context.homeBreadcrumb]}
            >
                <ReactMarkdown>{context.readme}</ReactMarkdown>
                <GithubButton />
            </ArticleLayout>
        </ContentLayout>
    );
};
