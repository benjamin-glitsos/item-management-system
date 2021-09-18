import { Grid, Row, Col } from "react-flexbox-grid";
import styled from "styled-components";

export default ({ sidebar, children }) => (
    <StyledGrid fluid>
        <Row>
            <Col lg={10}>{children}</Col>
            <Col lg={2}>{sidebar}</Col>
        </Row>
    </StyledGrid>
);

const StyledGrid = styled(Grid)`
    margin: auto -31px;
`;
