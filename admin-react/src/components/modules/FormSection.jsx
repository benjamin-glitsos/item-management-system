import { Fragment } from "react";
import { Col } from "react-flexbox-grid";

export default ({ title, children }) => {
    return (
        <Col sm={12}>
            <h3>{title}</h3>
            {children}
        </Col>
    );
};
