import { Suspense, lazy } from "react";
import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import Page from "%/components/Page/PagePresenter";
import LoadingPage from "%/components/LoadingPage";

const ReadmePage = lazy(() => import("%/components/ReadmePage"));
const UsersPage = lazy(() => import("%/components/UsersPage"));
const ItemsPage = lazy(() => import("%/components/ItemsPage"));
const NotFoundPage = lazy(() => import("%/components/NotFoundPage"));

export default () => (
    <Router history={createBrowserHistory()}>
        <Page>
            <Analytics id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}>
                <Suspense fallback={<LoadingPage />}>
                    <Switch>
                        <QueryParamProvider
                            ReactRouterRoute={Route}
                            exact
                            path="/"
                        >
                            <ReadmePage />
                        </QueryParamProvider>
                        <QueryParamProvider
                            ReactRouterRoute={Route}
                            path="/users"
                        >
                            <UsersPage />
                        </QueryParamProvider>
                        <QueryParamProvider
                            ReactRouterRoute={Route}
                            path="/items"
                        >
                            <ItemsPage />
                        </QueryParamProvider>
                        <QueryParamProvider
                            ReactRouterRoute={NotFoundPage}
                            path="*"
                        >
                            <NotFoundPage />
                        </QueryParamProvider>
                    </Switch>
                </Suspense>
            </Analytics>
        </Page>
    </Router>
);
