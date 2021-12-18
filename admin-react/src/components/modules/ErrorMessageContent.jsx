import Content from "elements/Content";
import ErrorMessage from "elements/ErrorMessage";

export default ({ maxWidth }) => (
    <Content maxWidth={maxWidth}>
        <ErrorMessage />
    </Content>
);
