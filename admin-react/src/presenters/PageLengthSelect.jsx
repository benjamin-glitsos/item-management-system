import styled from "styled-components";
import Select from "@atlaskit/select";
import config from "%/config";

export default ({ isVisible, pageLength, setPageLength }) => {
    if (isVisible) {
        return (
            <Styles>
                <Select
                    options={config.pageLengths.map(n => ({
                        label: n,
                        value: n
                    }))}
                    value={pageLength}
                    placeholder={`${pageLength} per page`}
                    onChange={setPageLength}
                    spacing="compact"
                />
            </Styles>
        );
    } else {
        return null;
    }
};

const Styles = styled.div`
    & > div {
        min-width: 120px;
    }
`;
