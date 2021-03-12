import composeHooks from "react-hooks-compose";
import RouterLocationContainer from "%/containers/RouterLocation";
import SidebarNavigationPresenter from "%/presenters/SidebarNavigationPresenter";

export default () => {
    const Component = composeHooks({ RouterLocationContainer })(
        SidebarNavigationPresenter
    );
    return <Component />;
};
