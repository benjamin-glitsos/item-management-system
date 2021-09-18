import { Fragment, useContext } from "react";
import { Link } from "react-router-dom";
import formatDate from "%/utilities/formatDate";
import { OpenContext } from "%/components/Open/Open";
import styled from "styled-components";

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

export default ({ context }) => {
    const cx = useContext(context);

    return (
        <Fragment>
            <Item label="Created">
                <Author
                    at={cx.usersData.created_at}
                    by={cx.usersData.created_by}
                />
            </Item>
            <Item label="Opened">
                <Author
                    at={cx.usersData.edited_at}
                    by={cx.usersData.edited_by}
                />
            </Item>
            <Item label="Deleted">
                <Author
                    at={cx.usersData.deleted_at}
                    by={cx.usersData.deleted_by}
                />
            </Item>
            <Item label="Edits">{cx.usersData.edits}</Item>
            <Item label="Opens">{cx.usersData.opens}</Item>
            <Item label="Metakey">{cx.usersData.metakey}</Item>
        </Fragment>
    );
};

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
