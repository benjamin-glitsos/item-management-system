import { Fragment } from "react";
import { Row, Col } from "react-flexbox-grid";
import styled from "styled-components";

export default ({ title, children }) => {
    return (
        <Fragment>
            <Col sm={12}>
                <Heading>{title}</Heading>
            </Col>
            {children}
        </Fragment>
    );
};

const Heading = styled.h3`
    padding-top: 1rem;
`;
