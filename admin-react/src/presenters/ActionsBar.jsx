import ButtonGroup from "@atlaskit/button/button-group";
import Button from "@atlaskit/button/standard-button";
import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";

export default () => (
    <ButtonGroup>
        {/* <Button appearance="primary">Create New</Button> */}
        <DropdownMenu name="Delete" triggerType="button">
            <DropdownItemGroup>
                <DropdownItem>Soft Delete</DropdownItem>
                <DropdownItem>Hard Delete</DropdownItem>
            </DropdownItemGroup>
        </DropdownMenu>
    </ButtonGroup>
);
