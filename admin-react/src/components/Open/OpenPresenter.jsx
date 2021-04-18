import { useContext } from "react";
import { OpenContext } from "%/components/Open/Open";
import OpenLayout from "%/components/OpenLayout";
import PageLayout from "%/components/PageLayout";
import generateBreadcrumbs from "%/utilities/generateBreadcrumbs";
import { titleCase } from "title-case";

export default ({ children }) => {
    const context = useContext(OpenContext);
    const title = `${titleCase(
        [context.action, context.nameSingular].join(" ")
    )} : ${context.key}`;
    return (
        <PageLayout
            title={`${title} : ${process.env.PROJECT_ABBREV || "IMS"}`}
            description={context.description}
        >
            <OpenLayout
                title={title}
                breadcrumbs={generateBreadcrumbs(context.homeBreadcrumb)}
            >
                {children}
            </OpenLayout>
        </PageLayout>
    );
};
