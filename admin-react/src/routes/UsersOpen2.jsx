import { Fragment } from "react";
import { useParams } from "react-router-dom";
import { titleCase } from "title-case";
import useOpen from "%/hooks/useOpen";
import useUser from "%/hooks/useUser";
import LoadingSpinner from "%/components/LoadingSpinner";

export default () => {
    const { username } = useParams();
    const user = useUser();
    const {
        isLoading,
        isError,
        error,
        data: { response, data }
    } = useOpen({
        path: [user.namePlural, username]
    });

    if (isLoading) {
        return <LoadingSpinner />;
    }

    if (isError) {
        return <div>Error</div>;
    }

    console.log(response, data);
    const res = response.data.data;
    return (
        <Fragment>
            <h1>
                {titleCase(`${data.action} user`)} : {res.username}
            </h1>
        </Fragment>
    );
};
