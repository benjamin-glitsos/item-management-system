import Breadcrumbs, { BreadcrumbsItem } from "@atlaskit/breadcrumbs";

export default ({ breadcrumbs }) => (
    <Breadcrumbs>
        {breadcrumbs.map(([title, path], i) => (
            <BreadcrumbsItem
                text={title}
                key={`${i}-${title}-breadcrumb`}
                href={path}
                isDisabled={i + 1 === breadcrumbs.length}
            />
        ))}
    </Breadcrumbs>
);
