import { Fragment } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import formatDate from "utilities/formatDate";

const Item = ({ label, children }) => (
    <Styles>
        <Label>{label.toUpperCase()}</Label>
        <div>{children}</div>
    </Styles>
);

const Author = ({ at, by }) => {
    if (at && by) {
        return (
            <Fragment>
                {formatDate(at)}
                by <Link to={`/users/${by}`}>{by}</Link>
            </Fragment>
        );
    } else {
        return formatDate();
    }
};

export default ({ data }) => (
    <Fragment>
        <Item label="Created">
            <Author at={data.created_at} by={data.created_by} />
        </Item>
        <Item label="Edited">
            <Author at={data.edited_at} by={data.edited_by} />
        </Item>
        <Item label="Deleted">
            <Author at={data.deleted_at} by={data.deleted_by} />
        </Item>
        <Item label="Edits">{data.edits}</Item>
        <Item label="Metakey">{data.metakey}</Item>
    </Fragment>
);

const Styles = styled.div`
    margin-top: 8px;
    margin-bottom: 1em;
`;

const Label = styled.div`
    font-weight: 600;
    font-size: 0.75em;
    margin-bottom: 2px;
    color: rgb(107, 119, 140);
`;
