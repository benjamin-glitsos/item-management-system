import { titleCase } from "title-case";
import joinTitle from "%/utilities/joinTitle";

export default ({ action, key, userNameSingular, projectName }) => {
    const pageTitle = joinTitle([
        titleCase(`${action} ${userNameSingular}`),
        key
    ]);

    const tabTitle = joinTitle([pageTitle, projectName]);

    const pageDescription = `A ${userNameSingular} in the ${projectName}.`;

    return { pageTitle, tabTitle, pageDescription };
};
