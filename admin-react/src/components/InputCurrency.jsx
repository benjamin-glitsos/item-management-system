import Textfield from "@atlaskit/textfield";
import styled from "styled-components";

const Symbol = () => <SymbolStyles>$</SymbolStyles>;

export default ({ ...props }) => (
    <Textfield
        type="number"
        step="any"
        elemBeforeInput={<Symbol />}
        {...props}
    />
);

const SymbolStyles = styled.div`
    margin-left: 0.5em;
    color: rgb(107, 119, 140);
`;
