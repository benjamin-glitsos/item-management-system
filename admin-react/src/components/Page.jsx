import { Fragment } from "react";
import { Helmet } from "react-helmet";
import styled from "styled-components";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";

export default ({ title, description, breadcrumbs, maxWidth, children }) => (
    <Fragment>
        <Helmet>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="shrink-to-fit=yes" />
        </Helmet>
        <Content maxWidth={maxWidth}>
            <PageHeader
                breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            >
                {title}
            </PageHeader>
            {children}
        </Content>
    </Fragment>
);

const Content = styled.div`
    margin: 0 auto 28px auto;
    max-width: ${props => props.maxWidth};
    padding: 0 3em;

    h1 {
        margin-bottom: -1em;
    }
`;
