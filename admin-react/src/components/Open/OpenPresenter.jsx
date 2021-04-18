import { useContext } from "react";
import { OpenContext } from "%/components/Open/Open";
import OpenLayout from "%/components/OpenLayout";
import generateBreadcrumbs from "%/utilities/generateBreadcrumbs";
import { titleCase } from "title-case";

export default ({ children }) => {
    const context = useContext(OpenContext);
    return (
        <OpenLayout
            title={titleCase(`${context.action} ${context.nameSingular}`)}
            breadcrumbs={generateBreadcrumbs(context.homeBreadcrumb)}
        >
            {children}
        </OpenLayout>
    );
};
