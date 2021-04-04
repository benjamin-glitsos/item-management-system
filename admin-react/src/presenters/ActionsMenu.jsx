import { useContext } from "react";
import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";
import { ListContext } from "%/components/List";
import friendlyAction from "%/utilities/friendlyAction";

export default ({ items }) => {
    const context = useContext(ListContext);
    const numberOfItems = items.length;
    const isDeletable = !context.isDataEmpty && numberOfItems > 0;
    const softDeleteAction = () => context.deleteItemsAction("soft", items);
    const hardDeleteAction = () => context.deleteItemsAction("hard", items);

    const actions = [
        {
            title: "Delete",
            onClick: softDeleteAction,
            isDisabled: isDeletable,
            isVisible: numberOfItems >= 1
        },
        {
            title: "Hard delete",
            onClick: hardDeleteAction,
            isDisabled: isDeletable,
            isVisible: numberOfItems >= 1
        }
    ].filter(a => a.isVisible);

    return (
        <DropdownMenu name="Actions" triggerType="button">
            <DropdownItemGroup>
                {actions.length >= 1 ? (
                    actions.map(a => {
                        if (a.length >= 1) {
                            return (
                                <DropdownItem
                                    onClick={a.onClick}
                                    isDisabled={a.isDisabled}
                                >
                                    {friendlyAction(numberOfItems, a.title)}
                                </DropdownItem>
                            );
                        }
                    })
                ) : (
                    <DropdownItem isDisabled={true}>
                        No actions available. Zero items selected.
                    </DropdownItem>
                )}
            </DropdownItemGroup>
        </DropdownMenu>
    );
};
