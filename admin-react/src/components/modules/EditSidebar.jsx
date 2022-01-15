import { Fragment, useContext } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { EditContext } from "templates/EditTemplate";
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
                {formatDate(at)} <strong>by</strong>{" "}
                <Link to={`/users/${by}`}>{by}</Link>
            </Fragment>
        );
    } else {
        return formatDate();
    }
};

export default () => {
    const context = useContext(EditContext);
    return (
        <Fragment>
            <Item label="Created">
                <Author
                    at={context.itemData.created_at}
                    by={context.itemData.created_by}
                />
            </Item>
            <Item label="Edited">
                <Author
                    at={context.itemData.edited_at}
                    by={context.itemData.edited_by}
                />
            </Item>
            <Item label="Deleted">
                <Author
                    at={context.itemData.deleted_at}
                    by={context.itemData.deleted_by}
                />
            </Item>
            <Item label="Edits">{context.itemData.edits}</Item>
            <Item label="Metakey">{context.itemData.metakey}</Item>
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
