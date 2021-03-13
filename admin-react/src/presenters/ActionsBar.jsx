import ButtonGroup from "@atlaskit/button/button-group";
import Button from "@atlaskit/button/standard-button";
import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";

export default (isDeletable, softDeleteAction, hardDeleteAction) => (
    <ButtonGroup>
        {/* <Button appearance="primary">Create New</Button> */}
        <DropdownMenu name="Delete" triggerType="button">
            <DropdownItemGroup>
                <DropdownItem
                    onClick={softDeleteAction}
                    isDisabled={isDeletable}
                >
                    Soft Delete
                </DropdownItem>
                <DropdownItem
                    onClick={hardDeleteAction}
                    isDisabled={isDeletable}
                >
                    Hard Delete
                </DropdownItem>
            </DropdownItemGroup>
        </DropdownMenu>
    </ButtonGroup>
);
