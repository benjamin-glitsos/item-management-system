import { titleCase } from "title-case";
import joinTitle from "%/utilities/joinTitle";

export default ({ action, key, userNameSingular, projectName }) => {
    const actionTitle = joinTitle([
        titleCase(`${action} ${userNameSingular}`),
        key
    ]);

    const pageTitle = joinTitle([action, projectName]);

    const pageDescription = `A ${userNameSingular} in the ${projectName}.`;

    return { actionTitle, pageTitle, pageDescription };
};
