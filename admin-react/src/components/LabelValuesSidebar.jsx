import styled from "styled-components";
import LabelValueItem from "%/components/LabelValueItem";

export default ({ items }) => (
    <Styles>
        {Object.entries(items).map(([label, value], i) => (
            <LabelValueItem
                key={`LabelValueItem/${label},${i}`}
                label={label}
                value={value}
            />
        ))}
    </Styles>
);

const Styles = styled.div`
    margin-top: 8px;
`;
