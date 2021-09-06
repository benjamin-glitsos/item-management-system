import { titleCase } from "title-case";
import joinTitle from "%/utilities/joinTitle";

export default ({
    history,
    action,
    key,
    nameSingular,
    namePlural,
    projectName
}) => {
    const pageTitle = joinTitle([titleCase(`${action} ${nameSingular}`), key]);

    const tabTitle = joinTitle([pageTitle, projectName]);

    const pageDescription = `A ${nameSingular} in the ${projectName}.`;

    const handleReturn = () => {
        history.push(`/${namePlural}`);
    };

    return { pageTitle, tabTitle, pageDescription, handleReturn };
};
