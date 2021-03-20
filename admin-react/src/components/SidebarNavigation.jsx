import { createContext } from "react";
import RouterLocationContainer from "%/containers/RouterLocationContainer";
import SidebarNavigationPresenter from "%/presenters/SidebarNavigationPresenter";

export const SidebarNavigationContext = createContext();

const { Provider } = SidebarNavigationContext;

export default () => (
    <Provider value={RouterLocationContainer()}>
        <SidebarNavigationPresenter />
    </Provider>
);
