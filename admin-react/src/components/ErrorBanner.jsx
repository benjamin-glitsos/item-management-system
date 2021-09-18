import styled from "styled-components";
import { useHistory } from "react-router-dom";
import { Grid, Row, Col } from "react-flexbox-grid";
import Button, { ButtonGroup } from "@atlaskit/button";
import makeImagePath from "%/utilities/makeImagePath";

export default ({ goBackUrl, title, description }) => {
    const history = useHistory();
    const handleReload = () => window.location.reload();
    const handleGoBack = () => history.push(goBackUrl || "/");

    return (
        <Styles>
            <Grid fluid>
                <Row middle="xs">
                    <Col sm={6}>
                        <img
                            src={makeImagePath(["error.jpg"])}
                            alt="Illustration that represents an 'error' - a man picks up a shape that has fallen to the ground"
                        />
                    </Col>
                    <Col sm={6}>
                        <h2>{title || "Oh no! Something went wrong"}</h2>
                        <p>
                            {description ||
                                "An error occurred; sorry about that."}
                        </p>
                        <ButtonGroup>
                            <Button appearance="primary" onClick={handleGoBack}>
                                Go back
                            </Button>
                            <Button appearance="subtle" onClick={handleReload}>
                                Reload page
                            </Button>
                        </ButtonGroup>
                    </Col>
                </Row>
            </Grid>
        </Styles>
    );
};

const Styles = styled.div`
    margin: 0 auto;
    max-width: 48em;
    padding-top: 4rem;
    padding-bottom: 4rem;

    img {
        max-width: 260px;
    }

    p {
        margin-bottom: 24px;
    }
`;
