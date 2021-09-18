import { Fragment } from "react";
import { useHistory } from "react-router-dom";
import ErrorImage from "%/components/ErrorImage";
import Button, { ButtonGroup } from "@atlaskit/button";

export default ({ goBackUrl, title, description }) => {
    const history = useHistory();
    const handleReload = () => window.location.reload();
    const handleGoBack = () => history.push(goBackUrl || "/");

    return (
        <Fragment>
            <ErrorImage />
            <h2>{title || "Oh no! Something went wrong"}</h2>
            {description || (
                <div>An error occurred&ndash;sorry about that.</div>
            )}
            <ButtonGroup>
                <Button appearance="subtle" onClick={handleReload}>
                    Reload page
                </Button>
                <Button appearance="primary" onClick={handleGoBack}>
                    Go back
                </Button>
            </ButtonGroup>
        </Fragment>
    );
};
