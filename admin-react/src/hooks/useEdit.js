import { titleCase } from "title-case";

export default () => {
    const action = "edit";

    const maxWidth = "1200px";

    const breadcrumb = [titleCase(action), "."];

    return { action, maxWidth, breadcrumb };
};
