import { useContext } from "react";
import styled from "styled-components";
import { titleCase } from "title-case";
import { Grid, Row, Col } from "react-flexbox-grid";
import { OpenContext } from "%/components/Open";
import OpenLayout from "%/components/OpenLayout";
import PageLayout from "%/components/PageLayout";
import FormButtons from "%/components/FormButtons";
import LoadingBanner from "%/components/LoadingBanner";
import OpenSidebar from "%/components/OpenSidebar";

export default ({ children }) => {
    const context = useContext(OpenContext);
    const title = `${titleCase(
        [context.action, context.nameSingular].join(" ")
    )}${!context.isCreate ? ` : ${context.key}` : ""}`;
    const isCreate = context.isCreate;
    const isLoading = context.state.isLoading;
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
                isLoading={isLoading}
            >
                {!isCreate && isLoading ? (
                    <LoadingBanner />
                ) : (
                    <OffsetGridOuterPadding>
                        <Grid fluid>
                            <Row>
                                <Col sm={10}>
                                    <form
                                        onSubmit={context.handleSubmit(
                                            context.onSubmit
                                        )}
                                    >
                                        <Grid fluid>
                                            <Row>{children}</Row>
                                            <Row end="xs">
                                                <Col sm={12}>
                                                    <FormButtons
                                                        cancelHandler={
                                                            context.cancelHandler
                                                        }
                                                    />
                                                </Col>
                                            </Row>
                                        </Grid>
                                    </form>
                                </Col>
                                <Col sm={2}>{!isCreate && <OpenSidebar />}</Col>
                            </Row>
                        </Grid>
                    </OffsetGridOuterPadding>
                )}
            </OpenLayout>
        </PageLayout>
    );
};

const OffsetGridOuterPadding = styled.div`
    margin-left: -32px;
    margin-right: -32px;
`;
