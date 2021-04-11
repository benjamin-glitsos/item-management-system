import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/components/DropdownMenu";
import friendlyAction from "%/utilities/friendlyAction";

export default ({ items, additionalItems = [], deleteItemsAction }) => {
    const numberOfItems = items.length;
    const isDeletable = numberOfItems > 0;
    const softDeleteAction = () => deleteItemsAction("soft", items);
    const hardDeleteAction = () => deleteItemsAction("hard", items);

    const Actions = () => {
        const items = [
            {
                title: "Delete",
                onClick: softDeleteAction,
                isVisible: isDeletable
            },
            {
                title: "Hard delete",
                onClick: hardDeleteAction,
                isVisible: isDeletable
            },
            ...additionalItems
        ];

        const filtered = items.filter(a => a.isVisible);

        if (filtered.length >= 1) {
            return filtered.map(a => {
                return (
                    <DropdownItem onClick={a.onClick}>
                        {friendlyAction(numberOfItems, a.title)}
                    </DropdownItem>
                );
            });
        } else {
            return (
                <DropdownItem isDisabled={true}>
                    No actions available. {friendlyAction(numberOfItems, "")}.
                </DropdownItem>
            );
        }
    };

    return (
        <DropdownMenu name="Actions" triggerType="button">
            <DropdownItemGroup>
                <Actions />
            </DropdownItemGroup>
        </DropdownMenu>
    );
};
