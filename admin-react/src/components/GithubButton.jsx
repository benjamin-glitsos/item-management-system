import styled from "styled-components";
import AtlaskitButton from "@atlaskit/button";
import GithubIcon from "%/components/GithubIcon";

export default () => (
    <Button
        appearance="link"
        href={
            process.env.REACT_APP_PROJECT_GIT_REPO_URL ||
            "https://github.com/benjamin-glitsos/item-management-system"
        }
        target="_blank"
        iconBefore={<GithubIcon />}
    >
        View the code on GitHub
    </Button>
);

const Button = styled(AtlaskitButton)`
    margin-top: 28px;

    .link-icon {
        fill: #0052cc;
    }

    a:hover .link-icon {
        fill: #0065ff;
    }
`;
