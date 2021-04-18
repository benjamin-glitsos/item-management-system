import { createContext } from "react";
import OpenPresenter from "%/components/Open/OpenPresenter";

export const ListContext = createContext();

const { Provider } = ListContext;

export default ({ context, children }) => (
    <Provider value={context}>
        <OpenPresenter>{children}</OpenPresenter>
    </Provider>
);
