import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/components/DropdownMenu";
import formatAction from "%/utilities/formatAction";

export default ({
    items,
    additionalItems = [],
    deleteItemsAction,
    setDeselectAll
}) => {
    const numberOfItems = items.length;
    const isDeletable = numberOfItems > 0;
    const deleteItemsActionHandler = method => {
        setDeselectAll();
        deleteItemsAction(method, items);
    };

    const Actions = () => {
        const items = [
            {
                title: "Delete",
                onClick: deleteItemsActionHandler("soft"),
                isVisible: isDeletable
            },
            {
                title: "Hard delete",
                onClick: deleteItemsActionHandler("hard"),
                isVisible: isDeletable
            },
            ...additionalItems
        ];

        const filtered = items.filter(a => a.isVisible);

        if (filtered.length >= 1) {
            return filtered.map(a => {
                return (
                    <DropdownItem onClick={a.onClick}>
                        {formatAction(numberOfItems, a.title)}
                    </DropdownItem>
                );
            });
        } else {
            return (
                <DropdownItem isDisabled={true}>
                    No actions available. {formatAction(numberOfItems, "")}.
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
