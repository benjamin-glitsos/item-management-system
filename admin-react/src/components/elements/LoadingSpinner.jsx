import styled from "styled-components";
import AtlaskitSpinner from "@atlaskit/spinner";

export default () => <Spinner size="large" delay={0} />;

const Spinner = styled(AtlaskitSpinner)`
    margin-top: 40px;

    svg {
        display: block;
        margin: 0 auto;
    }
`;
