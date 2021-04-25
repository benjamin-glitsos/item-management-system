import { Fragment, Suspense, lazy } from "react";
import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import "react-toastify/dist/ReactToastify.min.css";
import Page from "%/components/Page/PagePresenter";
import LoadingSpinner from "%/components/LoadingSpinner";
import ToastContainer from "%/components/ToastContainer";

const ReadmePage = lazy(() => import("%/components/ReadmePage"));
const UsersListPage = lazy(() => import("%/components/UsersListPage"));
const UsersOpenPage = lazy(() => import("%/components/UsersOpenPage"));
const ItemsListPage = lazy(() => import("%/components/ItemsListPage"));
const ItemsOpenPage = lazy(() => import("%/components/ItemsOpenPage"));
const NotFoundPage = lazy(() => import("%/components/NotFoundPage"));

export default () => (
    <Router history={createBrowserHistory()}>
        <Analytics id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}>
            <QueryParamProvider>
                <Page>
                    <Suspense fallback={<LoadingSpinner />}>
                        <Switch>
                            <Route exact path="/">
                                <ReadmePage />
                            </Route>
                            <Route exact path="/users">
                                <UsersListPage />
                            </Route>
                            <Route exact path="/users/:username">
                                <UsersOpenPage action="edit" />
                            </Route>
                            <Route exact path="/create-user">
                                <UsersOpenPage action="create" />
                            </Route>
                            <Route exact path="/items">
                                <ItemsListPage />
                            </Route>
                            <Route exact path="/items/:key">
                                <ItemsOpenPage action="edit" />
                            </Route>
                            <Route exact path="/create-item">
                                <ItemsOpenPage action="create" />
                            </Route>
                            <Route path="*">
                                <NotFoundPage />
                            </Route>
                        </Switch>
                    </Suspense>
                </Page>
                <ToastContainer />
            </QueryParamProvider>
        </Analytics>
    </Router>
);
