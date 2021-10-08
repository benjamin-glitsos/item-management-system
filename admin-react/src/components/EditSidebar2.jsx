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
                    at={cx.entityData.created_at}
                    by={cx.entityData.created_by}
                />
            </Item>
            <Item label="Edited">
                <Author
                    at={cx.entityData.edited_at}
                    by={cx.entityData.edited_by}
                />
            </Item>
            <Item label="Deleted">
                <Author
                    at={cx.entityData.deleted_at}
                    by={cx.entityData.deleted_by}
                />
            </Item>
            <Item label="Edits">{cx.entityData.edits}</Item>
            <Item label="Opens">{cx.entityData.opens}</Item>
            <Item label="Metakey">{cx.entityData.metakey}</Item>
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
