import { Grid, Row, Col } from "react-flexbox-grid";
import styled from "styled-components";

export default ({ label, value }) => (
    <Grid fluid>
        <Row>
            <Col sm={6}>
                <Label>{label}</Label>
            </Col>
            <Col sm={6}>{value}</Col>
        </Row>
    </Grid>
);

const Label = styled.div`
    font-weight: bold;
    margin-bottom: 8px;
`;
