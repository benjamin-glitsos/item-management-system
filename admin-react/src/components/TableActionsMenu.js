import DropdownMenu, {
    DropdownItem,
    DropdownItemGroup
} from "@atlaskit/dropdown-menu";

export default () => (
    <DropdownMenu trigger="Actions" triggerType="button">
        <DropdownItemGroup>
            <DropdownItem>Edit</DropdownItem>
            <DropdownItem>Delete</DropdownItem>
        </DropdownItemGroup>
    </DropdownMenu>
);
