import styled from "styled-components";
import AtlaskitDropdownMenu from "@atlaskit/dropdown-menu";

export default ({ name, children }) => (
    <DropdownMenu trigger={name} triggerType="button">
        {children}
    </DropdownMenu>
);

const DropdownMenu = styled(AtlaskitDropdownMenu)`
    [role="menuitem"] {
        min-width: 120px;
        z-index: 4;
    }
`;
