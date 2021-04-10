import styled from "styled-components";
import EditorCloseIcon from "@atlaskit/icon/glyph/editor/close";

export default ({ doesSelectionExist, action }) => {
    if (doesSelectionExist) {
        return (
            <Icon onClick={action}>
                <EditorCloseIcon size="small" label="Clear all selections." />
            </Icon>
        );
    } else {
        return null;
    }
};

const Icon = styled.div`
    & > span {
        margin-top: -6px;
        margin-left: 1.5px;
        position: relative;
        top: 4px;
    }

    & > span,
    & > span > svg {
        height: 21px;
        width: 21px;
    }
`;
