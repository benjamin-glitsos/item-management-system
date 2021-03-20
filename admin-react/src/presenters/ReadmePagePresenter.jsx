import ReactMarkdown from "react-markdown";
import readme from "%/assets/README.md";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import GithubButton from "%/presenters/GithubButton";

export default ({ title, description }) => (
    <PageLayout title={title} description={description}>
        <ArticleLayout title={title} breadcrumbs={[["Home", "/"]]}>
            <ReactMarkdown>{readme}</ReactMarkdown>
            <GithubButton />
        </ArticleLayout>
    </PageLayout>
);
