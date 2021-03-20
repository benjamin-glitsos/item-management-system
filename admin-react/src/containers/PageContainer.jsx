import { createContext } from "react";

export default ({ title, slug, description }) => {
    const metaTitle = `${title} : ${process.env.PROJECT_ABBREV || "IMS"}`;

    const homeBreadcrumb = ["Home", "/"];

    const { Provider: ContextProvider } = createContext();

    return {
        title,
        slug,
        metaTitle,
        description,
        homeBreadcrumb,
        ContextProvider
    };
};
