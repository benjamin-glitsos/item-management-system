import Button from "@atlaskit/button";
import GithubIcon from "%/presenters/GithubIcon";

export default () => (
    <Button
        appearance="primary"
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
