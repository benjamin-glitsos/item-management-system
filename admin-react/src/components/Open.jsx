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

    return (
        <Fragment>
            <h1>Open2</h1>
        </Fragment>
    );
};
