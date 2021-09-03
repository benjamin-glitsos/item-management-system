import styled from "styled-components";
import Button, { ButtonGroup } from "@atlaskit/button";

export default ({ cancelHandler }) => (
    <Styles>
        <ButtonGroup>
            <Button appearance="subtle" onClick={cancelHandler}>
                Cancel
            </Button>
            <Button type="submit" appearance="primary">
                Submit
            </Button>
        </ButtonGroup>
    </Styles>
);

const Styles = styled.div`
    margin-top: 24px;
`;
