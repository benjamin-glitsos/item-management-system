import { titleCase } from "title-case";

export default homeBreadcrumb => {
    const pathBreadcrumbs = location.pathname.split("/").filter(s => s !== "");
    return [
        homeBreadcrumb,
        ...pathBreadcrumbs.map((s, i) => {
            const path = s;
            const title = i + 1 !== pathBreadcrumbs.length ? titleCase(s) : s;
            return [title, path];
        })
    ];
};
