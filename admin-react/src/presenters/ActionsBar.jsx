import { useContext } from "react";
import DeletionMenu from "%/presenters/DeletionMenu";
import PageLengthSelect from "%/presenters/PageLengthSelect";
import ButtonGroup from "@atlaskit/button/button-group";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const doesDataExist = context.doesDataExist;
    const isDeletable = context.state.selected.length > 0;
    const softDeleteAction = () =>
        context.deleteItemsAction("soft", context.state.selected);
    const hardDeleteAction = () =>
        context.deleteItemsAction("hard", context.state.selected);
    const pageLength = context.state.response.data.page_length;
    const totalPages = context.state.response.data.total_pages;
    const setPageLength = context.setPageLength;
    return (
        <ButtonGroup>
            <DeletionMenu
                isVisible={doesDataExist}
                isDeletable={isDeletable}
                softDeleteAction={softDeleteAction}
                hardDeleteAction={hardDeleteAction}
            />
            <PageLengthSelect
                isVisible={doesDataExist}
                pageLength={pageLength}
                setPageLength={setPageLength}
            />
        </ButtonGroup>
    );
};
