import { useContext } from "react";
import styled from "styled-components";
import { titleCase } from "title-case";
import { Grid, Row, Col } from "react-flexbox-grid";
import { OpenContext } from "%/components/Open/Open";
import OpenLayout from "%/components/OpenLayout";
import PageLayout from "%/components/PageLayout";
import FormButtons from "%/components/FormButtons";
import LoadingSpinner from "%/components/LoadingSpinner";
import OpenSidebarItem from "%/components/OpenSidebarItem";
import UpdateAuthorDetails from "%/components/UpdateAuthorDetails";

export default ({ children }) => {
    const context = useContext(OpenContext);
    const title = `${titleCase(
        [context.action, context.nameSingular].join(" ")
    )}${!context.isCreate ? `: ${context.key}` : ""}`;
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
            >
                {!isCreate && isLoading ? (
                    <LoadingSpinner />
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
                                <Col sm={2}>
                                    <OpenSidebarItem label="Created">
                                        <UpdateAuthorDetails
                                            at={context.state.item.created_at}
                                            by={context.state.item.created_by}
                                        />
                                    </OpenSidebarItem>
                                    <OpenSidebarItem label="Edited">
                                        <UpdateAuthorDetails
                                            at={context.state.item.edited_at}
                                            by={context.state.item.edited_by}
                                        />
                                    </OpenSidebarItem>
                                    <OpenSidebarItem label="Deleted">
                                        <UpdateAuthorDetails
                                            at={context.state.item.deleted_at}
                                            by={context.state.item.deleted_by}
                                        />
                                    </OpenSidebarItem>
                                    <OpenSidebarItem label="Opens">
                                        {context.state.item.opens}
                                    </OpenSidebarItem>
                                    <OpenSidebarItem label="Edits">
                                        {context.state.item.edits}
                                    </OpenSidebarItem>
                                    <OpenSidebarItem label="Metakey">
                                        {context.state.item.metakey}
                                    </OpenSidebarItem>
                                </Col>
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
