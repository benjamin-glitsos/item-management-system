import composeHooks from "react-hooks-compose";
import ReadmePageContainer from "%/containers/ReadmePageContainer";
import ReadmePagePresenter from "%/presenters/ReadmePagePresenter";

export default () => {
    const Component = composeHooks({ ReadmePageContainer })(
        ReadmePagePresenter
    );
    return <Component />;
};
