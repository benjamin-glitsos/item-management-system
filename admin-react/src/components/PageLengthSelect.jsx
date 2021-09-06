import styled from "styled-components";
import AtlaskitSelect from "@atlaskit/select";
import config from "%/config";

export default ({ isDisabled, pageLength, setPageLength, setDeselectAll }) => (
    <Select
        options={config.pageLengths.map(n => ({
            label: n,
            value: n,
            isDisabled
        }))}
        value={pageLength}
        placeholder={`${pageLength || config.defaultPageLength} per page`}
        onChange={selection => {
            setDeselectAll();
            setPageLength(selection.value);
        }}
        spacing="compact"
    />
);

const Select = styled(AtlaskitSelect)`
    & > div {
        min-width: 120px;
    }
`;
