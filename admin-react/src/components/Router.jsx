import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Page from "%/presenters/PagePresenter";
import ReadmePage from "%/components/ReadmePage";
import UsersPage from "%/components/UsersPage";

export default () => (
    <Router history={createBrowserHistory()}>
        <Page>
            <Switch>
                <QueryParamProvider ReactRouterRoute={Route} exact path="/">
                    <ReadmePage />
                </QueryParamProvider>
                <QueryParamProvider ReactRouterRoute={Route} path="/users">
                    <UsersPage />
                </QueryParamProvider>
            </Switch>
        </Page>
    </Router>
);
