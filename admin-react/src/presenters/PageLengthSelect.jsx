import styled from "styled-components";
import Select from "@atlaskit/select";

export default ({ isVisible, pageLength, setPageLength }) => {
    if (isVisible) {
        return (
            <Styles>
                <Select
                    options={[10, 25, 50, 100].map(n => ({
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
