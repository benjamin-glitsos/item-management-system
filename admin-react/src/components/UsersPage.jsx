import composeHooks from "react-hooks-compose";
import UsersPageContainer from "%/containers/UsersPageContainer";
import UsersPagePresenter from "%/presenters/UsersPagePresenter";

export default () => {
    const Provider = UsersPageContainer.ContextProvider;
    const Component = composeHooks({ UsersPageContainer })(UsersPagePresenter);
    return <Component />;
};
