import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";

export default ({
    isVisible,
    isDeletable,
    softDeleteAction,
    hardDeleteAction
}) => {
    if (isVisible) {
        return (
            <DropdownMenu name="Deletion" triggerType="button">
                <DropdownItemGroup>
                    <DropdownItem
                        onClick={softDeleteAction}
                        isDisabled={!isDeletable}
                    >
                        Soft Delete
                    </DropdownItem>
                    <DropdownItem
                        onClick={hardDeleteAction}
                        isDisabled={!isDeletable}
                    >
                        Hard Delete
                    </DropdownItem>
                </DropdownItemGroup>
            </DropdownMenu>
        );
    } else {
        return null;
    }
};
