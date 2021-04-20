import { Fragment } from "react";
import styled from "styled-components";

export default ({ title, description }) => (
    <Fragment>
        <Title>{title}</Title>
        <Description>{description}</Description>
    </Fragment>
);

const Title = styled.div`
    font-weight: bold;
    margin-bottom: 2px;
    color: #42526e;
`;

const Description = styled.div`
    color: #6b778c;
`;
