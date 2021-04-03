import { useContext } from "react";
import { useHistory, useLocation } from "react-router-dom";
import styled from "styled-components";
import Button from "@atlaskit/button";
import PageLayout from "%/presenters/PageLayout";
import ArticleLayout from "%/presenters/ArticleLayout";
import { NotFoundContext } from "%/components/NotFoundPage";

export default () => {
    const context = useContext(NotFoundContext);

    const location = useLocation();
    const breadcrumbs = [
        context.homeBreadcrumb,
        ...location.pathname
            .split("/")
            .filter(s => s !== "")
            .map(s => [s, s])
    ];

    const history = useHistory();
    const handleReturnHome = () => {
        history.push("/");
    };

    return (
        <PageLayout title={context.metaTitle} description={context.description}>
            <ArticleLayout title={context.title} breadcrumbs={breadcrumbs}>
                <p>{context.description}</p>
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
