import { Fragment } from "react";
import { useLocation } from "react-router-dom";
import ErrorBanner from "%/components/ErrorBanner";

export default () => {
    const location = useLocation();

    return (
        <ErrorBanner
            title="Page not found"
            description={
                <Fragment>
                    A page does not exist at the current address: <br />
                    <strong>{location.pathname}</strong>
                </Fragment>
            }
            enableReloadPageButton={false}
        />
    );
};
