import { createContext } from "react";
import NotFoundPageContainer from "%/containers/NotFoundPageContainer";
import NotFoundPagePresenter from "%/presenters/NotFoundPagePresenter";

export const NotFoundContext = createContext();

const { Provider } = NotFoundContext;

export default () => (
    <Provider value={NotFoundPageContainer()}>
        <NotFoundPagePresenter />
    </Provider>
);
