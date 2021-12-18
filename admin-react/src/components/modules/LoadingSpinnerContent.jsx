import Content from "elements/Content";
import LoadingSpinner from "elements/LoadingSpinner";

export default ({ maxWidth }) => (
    <Content maxWidth={maxWidth}>
        <LoadingSpinner />
    </Content>
);
