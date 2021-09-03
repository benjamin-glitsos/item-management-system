import styled from "styled-components";
import Button, { ButtonGroup as AtlaskitButtonGroup } from "@atlaskit/button";

export default ({ cancelHandler }) => (
    <ButtonGroup>
        <Button appearance="subtle" onClick={cancelHandler}>
            Cancel
        </Button>
        <Button type="submit" appearance="primary">
            Submit
        </Button>
    </ButtonGroup>
);

const ButtonGroup = styled(AtlaskitButtonGroup)`
    margin-top: 24px;
`;
