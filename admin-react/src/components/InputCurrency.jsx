import Textfield from "@atlaskit/textfield";
import styled from "styled-components";

const CurrencySymbol = () => <Symbol>$</Symbol>;

export default ({ ...props }) => (
    <Textfield
        type="number"
        step="any"
        elemBeforeInput={<CurrencySymbol />}
        {...props}
    />
);

const Symbol = styled.div`
    margin-left: 0.5em;
    color: rgb(107, 119, 140);
`;
