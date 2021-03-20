import composeHooks from "react-hooks-compose";
import UsersPageContainer from "%/containers/UsersPageContainer";
import UsersPagePresenter from "%/presenters/UsersPagePresenter";

export default () => (
    <UsersPageContainer>
        <UsersPagePresenter />
    </UsersPageContainer>
);
