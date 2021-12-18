import { Fragment, useContext } from "react";
import { OpenContext } from "%/components/Open";
import OpenSidebarItem from "%/components/OpenSidebarItem";
import UpdateAuthorDetails from "%/components/UpdateAuthorDetails";

export default () => {
    const context = useContext(OpenContext);
    return (
        <Fragment>
            <OpenSidebarItem label="Created">
                <UpdateAuthorDetails
                    at={context.state.item.created_at}
                    by={context.state.item.created_by}
                />
            </OpenSidebarItem>
            <OpenSidebarItem label="Opened">
                <UpdateAuthorDetails
                    at={context.state.item.edited_at}
                    by={context.state.item.edited_by}
                />
            </OpenSidebarItem>
            <OpenSidebarItem label="Deleted">
                <UpdateAuthorDetails
                    at={context.state.item.deleted_at}
                    by={context.state.item.deleted_by}
                />
            </OpenSidebarItem>
            <OpenSidebarItem label="Opens">
                {context.state.item.opens}
            </OpenSidebarItem>
            <OpenSidebarItem label="Edits">
                {context.state.item.edits}
            </OpenSidebarItem>
            <OpenSidebarItem label="Metakey">
                {context.state.item.metakey}
            </OpenSidebarItem>
        </Fragment>
    );
};
