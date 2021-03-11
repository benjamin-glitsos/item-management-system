import ContentMargins from "../components/ContentMargins";
import BreadcrumbBar from "../components/BreadcrumbBar";
import ReactMarkdown from "react-markdown";
import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";
import PageHeader from "@atlaskit/page-header";
import readme from "../assets/README.md";

export default () => (
    <ContentMargins>
        <PageHeader
            breadcrumbs={<BreadcrumbBar breadcrumbs={[["Home", "/"]]} />}
        >
            Readme
        </PageHeader>
        <ReactMarkdown>{readme}</ReactMarkdown>
    </ContentMargins>
);
