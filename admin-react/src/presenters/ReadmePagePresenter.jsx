import { Fragment } from "react";
import { Helmet } from "react-helmet";
import ReactMarkdown from "react-markdown";
import PageHeader from "@atlaskit/page-header";
import readme from "%/assets/README.md";
import ArticleLayout from "%/presenters/ArticleLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import GithubButton from "%/presenters/GithubButton";

export default ({ title, description }) => (
    <Fragment>
        <Helmet>
            <title>
                {title} : {process.env.PROJECT_ABBREV || "IMS"}
            </title>
            <meta name="description" content={description} />
        </Helmet>
        <ArticleLayout>
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={[["Home", "/"]]} />}
            >
                {title}
            </PageHeader>
            <ReactMarkdown>{readme}</ReactMarkdown>
            <GithubButton />
        </ArticleLayout>
    </Fragment>
);
