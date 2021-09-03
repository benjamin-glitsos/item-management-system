import { Fragment } from "react";
import useOpen from "%/hooks/useOpen";

export default () => {
    const { isLoading, isError, error, data } = useOpen({
        path: "users/benglitsos"
    });

    if (isLoading) {
        return <span>Loading...</span>;
    }

    if (isError) {
        return <span>Error: {error.message}</span>;
    }

    console.log(data);
    return (
        <Fragment>
            <div>Open2:</div>
            <pre>{JSON.stringify(data)}</pre>
        </Fragment>
    );
};
