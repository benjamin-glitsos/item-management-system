import styled from "styled-components";
import Select from "@atlaskit/select";
import config from "%/config";

export default ({ isDisabled, pageLength, setPageLength, setDeselectAll }) => (
    <Styles>
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
    </Styles>
);

const Styles = styled.div`
    & > div {
        min-width: 120px;
    }
`;
