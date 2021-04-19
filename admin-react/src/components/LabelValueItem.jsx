import styled from "styled-components";

export default ({ label, value }) => (
    <Wrapper>
        <Label>{label.toUpperCase()}</Label>
        <div>{value}</div>
    </Wrapper>
);

const Wrapper = styled.div`
    margin-top: 8px;
    margin-bottom: 1em;
`;

const Label = styled.div`
    font-weight: 600;
    font-size: 0.75em;
    margin-bottom: 2px;
    color: rgb(107, 119, 140);
`;
