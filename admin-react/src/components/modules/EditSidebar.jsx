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

export default ({ usersQuery }) => (
    <Fragment>
        <Item label="Created">
            <Author at={usersQuery.created_at} by={usersQuery.created_by} />
        </Item>
        <Item label="Opened">
            <Author at={usersQuery.edited_at} by={usersQuery.edited_by} />
        </Item>
        <Item label="Deleted">
            <Author at={usersQuery.deleted_at} by={usersQuery.deleted_by} />
        </Item>
        <Item label="Opens">{usersQuery.opens}</Item>
        <Item label="Edits">{usersQuery.edits}</Item>
        <Item label="Metakey">{usersQuery.metakey}</Item>
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
