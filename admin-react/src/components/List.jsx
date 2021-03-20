import { createContext } from "react";
import ListPresenter from "%/presenters/ListPresenter";

export const ListContext = createContext();

const { Provider } = ListContext;

export default ({ context }) => (
    <Provider value={context}>
        <ListPresenter />
    </Provider>
);
