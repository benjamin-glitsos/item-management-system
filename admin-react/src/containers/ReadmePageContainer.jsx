import { createContext } from "react";
import PageContainer from "%/containers/PageContainer";

export const ReadmeContext = createContext();

export default ({ children }) => {
    const pageContainer = PageContainer({
        title: "Readme",
        description: `Information about the architecture and technology stack of the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });
    const { Provider } = ReadmeContext;

    return <Provider value={{ ...pageContainer }}>{children}</Provider>;
};
