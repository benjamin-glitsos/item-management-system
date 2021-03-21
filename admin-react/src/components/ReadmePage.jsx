import { createContext } from "react";
import ReadmePageContainer from "%/containers/ReadmePageContainer";
import ReadmePagePresenter from "%/presenters/ReadmePagePresenter";

export const ReadmeContext = createContext();

const { Provider } = ReadmeContext;

export default () => (
    <Provider value={ReadmePageContainer()}>
        <ReadmePagePresenter />
    </Provider>
);
