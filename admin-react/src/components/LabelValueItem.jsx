import styled from "styled-components";

export default ({ label, value }) => (
    <p>
        <b>{label.toUpperCase()}</b>
        <div>{value}</div>
    </p>
);
