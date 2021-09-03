import styled from "styled-components";
import DropdownMenu from "@atlaskit/dropdown-menu";

export default ({ name, children }) => (
    <Styles>
        <DropdownMenu trigger={name} triggerType="button">
            {children}
        </DropdownMenu>
    </Styles>
);

const Styles = styled.div`
    [role="menuitem"] {
        min-width: 120px;
        z-index: 4;
    }
`;
