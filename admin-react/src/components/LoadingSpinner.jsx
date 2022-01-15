import styled from "styled-components";
import AtlaskitSpinner from "@atlaskit/spinner";

export default ({ size = "large" }) => <Spinner size={size} delay={0} />;

const Spinner = styled(AtlaskitSpinner)`
    margin-top: 40px;

    svg {
        display: block;
        margin: 0 auto;
    }
`;
