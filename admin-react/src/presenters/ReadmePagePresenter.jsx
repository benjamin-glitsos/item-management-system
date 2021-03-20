import ReactMarkdown from "react-markdown";
import PageHeader from "@atlaskit/page-header";
import readme from "%/assets/README.md";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import GithubButton from "%/presenters/GithubButton";

export default ({ title, description }) => (
    <PageLayout title={title} description={description}>
        <ArticleLayout>
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={[["Home", "/"]]} />}
            >
                {title}
            </PageHeader>
            <ReactMarkdown>{readme}</ReactMarkdown>
            <GithubButton />
        </ArticleLayout>
    </PageLayout>
);
