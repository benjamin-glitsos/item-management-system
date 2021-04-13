import { useParams } from "react-router-dom";

export default () => {
    const { username } = useParams();
    return `The username is: ${username}`;
};
