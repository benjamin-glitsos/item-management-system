import ReactMarkdown from "react-markdown";
import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import PageHeader from "@atlaskit/page-header";
import readme from "@/assets/README.md";
import ArticleLayout from "@/presenters/ArticleLayout";
import BreadcrumbBar from "@/presenters/BreadcrumbBar";

export default () => (
    <ArticleLayout>
        <PageHeader
            breadcrumbs={<BreadcrumbBar breadcrumbs={[["Home", "/"]]} />}
        >
            Readme
        </PageHeader>
        <ReactMarkdown>{readme}</ReactMarkdown>
    </ArticleLayout>
);
