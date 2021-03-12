import styled from "styled-components";
import { DropdownItem, DropdownItemGroup } from "@atlaskit/dropdown-menu";
import DropdownMenu from "%/presenters/DropdownMenu";

export default () => (
    <RightAligned>
        <DropdownMenu name="Actions" triggerType="button">
            <DropdownItemGroup>
                <DropdownItem>Edit</DropdownItem>
                <DropdownItem>Delete</DropdownItem>
                <DropdownItem>Hard Delete</DropdownItem>
            </DropdownItemGroup>
        </DropdownMenu>
    </RightAligned>
);

const RightAligned = styled.div`
    text-align: right;
    margin-right: 0.5em;

    span {
        text-align: left;
    }

    [role="menuitem"] {
        min-width: 110px;
    }
`;
