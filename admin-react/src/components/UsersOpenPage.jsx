import { useParams } from "react-router-dom";

export default () => {
    const { username } = useParams();
    // TODO: First check that every feature will work
    // TODO: Start with json-schema-to-yup
    return `The username is: ${username}`;
};
