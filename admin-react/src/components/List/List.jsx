import { createContext } from "react";
import ListPresenter from "%/components/List/ListPresenter";

export const ListContext = createContext();

const { Provider } = ListContext;

export default ({ context }) => (
    <Provider value={context}>
        <ListPresenter />
    </Provider>
);
