import { useHistory } from "react-router-dom";
import styled from "styled-components";
import Button from "@atlaskit/button";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";

export default () => {
    const history = useHistory();
    const handleReturnHome = () => {
        history.push("/");
    };

    const title = "Page not found";
    const description =
        "The page at this location does not exist. Please click on the button below to return to the homepage.";
    return (
        <PageLayout title={title} description={description}>
            <ArticleLayout title={title} breadcrumbs={["Home"]}>
                <p>{description}</p>
                <HomeButtonStyles>
                    <Button appearance="primary" onClick={handleReturnHome}>
                        Back to Home
                    </Button>
                </HomeButtonStyles>
            </ArticleLayout>
        </PageLayout>
    );
};

const HomeButtonStyles = styled.div`
    margin-top: 28px;
`;
