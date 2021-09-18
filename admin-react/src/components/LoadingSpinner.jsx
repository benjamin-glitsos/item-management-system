import styled from "styled-components";
import Spinner from "@atlaskit/spinner";

export default () => (
    <Styles>
        <Spinner size="large" delay={0} />
    </Styles>
);

const Styles = styled.div`
    margin-top: 40px;

    svg {
        display: block;
        margin: 0 auto;
    }
`;
