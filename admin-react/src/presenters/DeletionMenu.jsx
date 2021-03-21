import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";

export default ({ isDisabled, softDeleteAction, hardDeleteAction }) => (
    <DropdownMenu name="Deletion" triggerType="button">
        <DropdownItemGroup>
            <DropdownItem onClick={softDeleteAction} isDisabled={isDisabled}>
                Soft Delete
            </DropdownItem>
            <DropdownItem onClick={hardDeleteAction} isDisabled={isDisabled}>
                Hard Delete
            </DropdownItem>
        </DropdownItemGroup>
    </DropdownMenu>
);
