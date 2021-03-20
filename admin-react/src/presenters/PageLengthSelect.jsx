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
                />
            </Styles>
        );
    } else {
        return null;
    }
};

const Styles = styled.div`
    & > div {
        justify-content: right;
        margin-right: -8px;
    }
`;
