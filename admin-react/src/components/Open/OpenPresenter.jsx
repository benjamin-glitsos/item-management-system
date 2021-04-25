import { useContext } from "react";
import styled from "styled-components";
import { titleCase } from "title-case";
import { Grid, Row, Col } from "react-flexbox-grid";
import { OpenContext } from "%/components/Open/Open";
import OpenLayout from "%/components/OpenLayout";
import PageLayout from "%/components/PageLayout";
import FormButtons from "%/components/FormButtons";
import LabelValuesSidebar from "%/components/LabelValuesSidebar";
import LoadingSpinner from "%/components/LoadingSpinner";
import generateBreadcrumbs from "%/utilities/generateBreadcrumbs";

const LoadingHandler = ({ isCreate, isLoading, children }) => {
    if (!isCreate && isLoading) {
        return <LoadingSpinner spinnerSize="medium" />;
    } else {
        return children;
    }
};

export default ({ children }) => {
    const context = useContext(OpenContext);
    const title = `${titleCase(
        [context.action, context.nameSingular].join(" ")
    )}${!context.isCreate ? `: ${context.key}` : ""}`;
    const isCreate = context.isCreate;
    const isLoading = context.state.loading;
    return (
        <PageLayout
            title={`${title} : ${process.env.PROJECT_ABBREV || "IMS"}`}
            description={context.description}
        >
            <OpenLayout
                title={title}
                breadcrumbs={[
                    context.homeBreadcrumb,
                    [titleCase(context.namePlural), `./${context.namePlural}`],
                    context.isCreate
                        ? [titleCase(context.action), ""]
                        : [context.key, ""]
                ]}
            >
                <OffsetGridOuterPadding>
                    <Grid fluid>
                        <Row>
                            <Col sm={10}>
                                <LoadingHandler
                                    isCreate={isCreate}
                                    isLoading={isLoading}
                                >
                                    <form
                                        onSubmit={context.handleSubmit(
                                            context.onSubmit
                                        )}
                                    >
                                        {" "}
                                        <Grid fluid>
                                            {" "}
                                            <Row>{children}</Row>{" "}
                                            <Row end="xs">
                                                {" "}
                                                <Col sm={12}>
                                                    {" "}
                                                    <FormButtons
                                                        cancelHandler={
                                                            context.cancelHandler
                                                        }
                                                    />{" "}
                                                </Col>{" "}
                                            </Row>{" "}
                                        </Grid>{" "}
                                    </form>
                                </LoadingHandler>
                            </Col>
                            <Col sm={2}>
                                <LoadingHandler
                                    isCreate={isCreate}
                                    isLoading={isLoading}
                                >
                                    <LabelValuesSidebar
                                        items={context.sidebarItems}
                                    />
                                </LoadingHandler>
                            </Col>
                        </Row>
                    </Grid>
                </OffsetGridOuterPadding>
            </OpenLayout>
        </PageLayout>
    );
};

const OffsetGridOuterPadding = styled.div`
    margin-left: -32px;
    margin-right: -32px;
`;
