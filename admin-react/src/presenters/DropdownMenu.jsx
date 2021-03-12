import DropdownMenu from "@atlaskit/dropdown-menu";
import styled from "styled-components";

export default ({ name, children }) => (
    <Styles>
        <DropdownMenu trigger={name} triggerType="button">
            {children}
        </DropdownMenu>
    </Styles>
);

const Styles = styled.div`
    [role="menuitem"] {
        min-width: 110px;
    }
`;
