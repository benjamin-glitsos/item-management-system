import styled from "styled-components";
import Spinner from "@atlaskit/spinner";

export default () => (
    <Layout>
        <Spinner size="large" delay={0} />
    </Layout>
);

const Layout = styled.div`
    margin-top: 40px;

    svg {
        display: block;
        margin: 0 auto;
    }
`;
