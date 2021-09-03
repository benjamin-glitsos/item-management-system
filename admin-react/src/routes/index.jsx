import { Suspense, lazy } from "react";
import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import "react-toastify/dist/ReactToastify.min.css";
import Page from "%/components/Page/PagePresenter";
import LoadingSpinner from "%/components/LoadingSpinner";
import ToastContainer from "%/components/ToastContainer";

const Readme = lazy(() => import("%/routes/Readme"));
const UsersList = lazy(() => import("%/routes/UsersList"));
const UsersOpen = lazy(() => import("%/routes/UsersOpen"));
const ItemsList = lazy(() => import("%/routes/ItemsList"));
const ItemsOpen = lazy(() => import("%/routes/ItemsOpen"));
const NotFound = lazy(() => import("%/routes/NotFound"));

export default () => (
    <Router history={createBrowserHistory()}>
        <Analytics id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}>
            <QueryParamProvider>
                <Page>
                    <Suspense fallback={<LoadingSpinner />}>
                        <Switch>
                            <Route exact path="/">
                                <Readme />
                            </Route>
                            <Route exact path="/users">
                                <UsersList />
                            </Route>
                            <Route exact path="/users/:username">
                                <UsersOpen action="edit" />
                            </Route>
                            <Route exact path="/create-user">
                                <UsersOpen action="create" />
                            </Route>
                            <Route exact path="/items">
                                <ItemsList />
                            </Route>
                            <Route exact path="/items/:key">
                                <ItemsOpen action="edit" />
                            </Route>
                            <Route exact path="/create-item">
                                <ItemsOpen action="create" />
                            </Route>
                            <Route path="*" status={404}>
                                <NotFound />
                            </Route>
                        </Switch>
                    </Suspense>
                </Page>
                <ToastContainer />
            </QueryParamProvider>
        </Analytics>
    </Router>
);
