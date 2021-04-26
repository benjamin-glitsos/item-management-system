import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/components/DropdownMenu";
import formatAction from "%/utilities/formatAction";

export default ({
    namePlural,
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
            ...additionalItems,
            {
                title: "Delete",
                onClick: () => deleteItemsActionHandler("soft"),
                isVisible: isDeletable
            },
            {
                title: "Hard delete",
                onClick: () => deleteItemsActionHandler("hard"),
                isVisible: isDeletable
            }
        ];

        const filtered = items.filter(a => a.isVisible);

        if (filtered.length >= 1) {
            return filtered.map(({ title, onClick }, i) => {
                return (
                    <DropdownItem
                        onClick={onClick}
                        key={`DropdownItem/${title},${i}`}
                    >
                        {formatAction(numberOfItems, title, namePlural)}
                    </DropdownItem>
                );
            });
        } else {
            return (
                <DropdownItem isDisabled={true}>
                    {formatAction(numberOfItems, "", namePlural)}.
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
