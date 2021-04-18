import { Fragment } from "react";
import styled from "styled-components";

export default ({ label, value }) => (
    <Fragment>
        <Label>{label}</Label>
        <Value>{value}</Value>
    </Fragment>
);

const Label = styled.div`
    font-weight: bold;
`;

const Value = styled.div`
    margin-bottom: 1em;
`;
