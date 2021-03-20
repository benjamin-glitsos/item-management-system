import { createContext } from "react";
import UsersPageContainer from "%/containers/UsersPageContainer";
import UsersPagePresenter from "%/presenters/UsersPagePresenter";

export const UsersContext = createContext();

const { Provider } = UsersContext;

export default () => (
    <Provider value={UsersPageContainer()}>
        <UsersPagePresenter />
    </Provider>
);
