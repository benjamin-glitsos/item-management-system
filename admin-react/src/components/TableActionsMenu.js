import DropdownMenu, {
    DropdownItem,
    DropdownItemGroup
} from "@atlaskit/dropdown-menu";
import styled from "styled-components";

export default () => (
    <RightAligned>
        <DropdownMenu trigger="Actions" triggerType="button">
            <DropdownItemGroup>
                <DropdownItem>Edit</DropdownItem>
                <DropdownItem>Delete</DropdownItem>
            </DropdownItemGroup>
        </DropdownMenu>
    </RightAligned>
);

const RightAligned = styled.div`
    text-align: right;
    margin-right: 0.5em;
`;
