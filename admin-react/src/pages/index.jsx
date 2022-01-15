import { Suspense, lazy } from "react";
import { Router, Route, Switch } from "react-router";
import { createBrowserHistory } from "history";
import { QueryParamProvider } from "use-query-params";
import Analytics from "react-router-ga";
import "react-toastify/dist/ReactToastify.min.css";
import Page from "%/components/PagePresenter";
import LoadingSpinner from "%/components/LoadingSpinner";
import ToastContainer from "%/components/ToastContainer";
import { QueryClient, QueryClientProvider } from "react-query";

const queryClient = new QueryClient();

const Readme = lazy(() => import("./Readme"));
const UsersList = lazy(() => import("./UsersList"));
const UsersOpen = lazy(() => import("./UsersOpen"));
const UsersEdit = lazy(() => import("./UsersEdit"));
const ItemsList = lazy(() => import("./ItemsList"));
const ItemsOpen = lazy(() => import("./ItemsOpen"));
const NotFound = lazy(() => import("./NotFound"));

export default () => (
    <Router history={createBrowserHistory()}>
        <Analytics id={process.env.REACT_APP_PROJECT_GOOGLE_ANALYTICS_ID}>
            <QueryClientProvider client={queryClient}>
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
                                    <UsersEdit action="edit" />
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
            </QueryClientProvider>
        </Analytics>
    </Router>
);
