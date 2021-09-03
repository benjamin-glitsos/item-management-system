import { createElement } from "react";
import { Col } from "react-flexbox-grid";
import styled from "styled-components";

const Heading = ({ level, children }) =>
    createElement(`h${level}`, null, children);

export default ({ level, children }) => {
    return (
        <Col sm={12}>
            <Styles>
                <Heading level={level}>{children}</Heading>
            </Styles>
        </Col>
    );
};

const Styles = styled.div`
    margin-top: 1.5em;
`;
