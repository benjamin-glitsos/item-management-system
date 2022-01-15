import { createContext } from "react";
import OpenPresenter from "%/components/OpenPresenter";

export const OpenContext = createContext();

const { Provider } = OpenContext;

export default ({ context, children }) => (
    <Provider value={context}>
        <OpenPresenter>{children}</OpenPresenter>
    </Provider>
);
