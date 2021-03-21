import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import Page from "%/presenters/PagePresenter";
import ReadmePage from "%/components/ReadmePage";
import UsersPage from "%/components/UsersPage";

export default () => (
    <Router history={createBrowserHistory()}>
        <Page>
            <Analytics id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}>
                <Switch>
                    <QueryParamProvider ReactRouterRoute={Route} exact path="/">
                        <ReadmePage />
                    </QueryParamProvider>
                    <QueryParamProvider ReactRouterRoute={Route} path="/users">
                        <UsersPage />
                    </QueryParamProvider>
                </Switch>
            </Analytics>
        </Page>
    </Router>
);
