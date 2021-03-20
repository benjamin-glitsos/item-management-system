import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import ContentMargins from "%/presenters/ContentMargins";

export default ({ title, breadcrumbs, children }) => (
    <ContentMargins maxWidth="800px">
        <PageHeader breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}>
            {title}
        </PageHeader>
        {children}
    </ContentMargins>
);
