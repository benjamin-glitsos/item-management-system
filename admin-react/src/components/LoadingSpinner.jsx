import styled from "styled-components";
import Spinner from "@atlaskit/spinner";

export default () => (
    <Styles>
        <Spinner size="large" delay={0} />
        <p>Loading . . .</p>
    </Styles>
);

const Styles = styled.div`
    margin: 0 auto;
    max-width: 48em;
    padding-top: 20vh;
    padding-bottom: 4rem;
    text-align: center;

    p {
        padding-top: 2em;
        font-weight: bold;
        color: #6b778c;
    }
`;
