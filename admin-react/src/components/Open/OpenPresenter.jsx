import { useContext } from "react";
import { titleCase } from "title-case";
import { Grid, Row, Col } from "react-flexbox-grid";
import { OpenContext } from "%/components/Open/Open";
import OpenLayout from "%/components/OpenLayout";
import PageLayout from "%/components/PageLayout";
import FormButtons from "%/components/FormButtons";
import LabelValueItem from "%/components/LabelValueItem";
import generateBreadcrumbs from "%/utilities/generateBreadcrumbs";

export default ({ children }) => {
    const context = useContext(OpenContext);
    const title = `${titleCase(
        [context.action, context.nameSingular].join(" ")
    )} : ${context.key}`;
    return (
        <PageLayout
            title={`${title} : ${process.env.PROJECT_ABBREV || "IMS"}`}
            description={context.description}
        >
            <OpenLayout
                title={title}
                breadcrumbs={generateBreadcrumbs(context.homeBreadcrumb)}
            >
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
                                        <FormButtons
                                            cancelHandler={
                                                context.cancelHandler
                                            }
                                        />
                                    </Row>
                                </Grid>
                            </form>
                        </Col>
                        <Col sm={2}>
                            {Object.entries(context.sidebarItems).map(
                                ([label, value]) => (
                                    <LabelValueItem
                                        label={label}
                                        value={value}
                                    />
                                )
                            )}
                        </Col>
                    </Row>
                </Grid>
            </OpenLayout>
        </PageLayout>
    );
};
