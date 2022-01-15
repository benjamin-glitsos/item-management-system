import { Fragment, useContext } from "react";
import { EditContext } from "templates/EditTemplate";
import Author from "elements/Author";
import SidebarItem from "elements/SidebarItem";

export default () => {
    const context = useContext(EditContext);

    return (
        <Fragment>
            <SidebarItem label="Created">
                <Author
                    at={context.itemData.created_at}
                    by={context.itemData.created_by}
                />
            </SidebarItem>
            <SidebarItem label="Edited">
                <Author
                    at={context.itemData.edited_at}
                    by={context.itemData.edited_by}
                />
            </SidebarItem>
            <SidebarItem label="Deleted">
                <Author
                    at={context.itemData.deleted_at}
                    by={context.itemData.deleted_by}
                />
            </SidebarItem>
            <SidebarItem label="Edits">{context.itemData.edits}</SidebarItem>
            <SidebarItem label="Metakey">
                {context.itemData.metakey}
            </SidebarItem>
        </Fragment>
    );
};
