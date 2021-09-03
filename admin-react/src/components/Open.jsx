import { Fragment } from "react";
import useOpen from "%/hooks/useOpen";
import LoadingSpinner from "%/components/LoadingSpinner";

export default () => {
    const { isLoading, isError, error, data } = useOpen({
        path: ["users", "benglitsos"]
    });

    if (isLoading) {
        return <LoadingSpinner />;
    }

    if (isError) {
        return <div>Error</div>;
    }

    const d = data.data.data;
    return (
        <Fragment>
            <h1>Edit user: {d.username}</h1>
        </Fragment>
    );
};
