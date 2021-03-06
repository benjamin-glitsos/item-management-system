import { lazy, Suspense } from "react";
import { Router, Route, Switch } from "react-router";
import createBrowserHistory from "history/createBrowserHistory";
import App from "./App";
import pages from "../data/pages";

const dynamicPage = () => (
    <Suspense fallback={<div>Loading...</div>}>
        {lazy(() => import("../pages/UsersPage"))}
    </Suspense>
);

export default () => (
    <Router history={createBrowserHistory()}>
        <App>
            <Switch>
                {Object.values(pages)
                    .flat()
                    .map(([title, path, Icon, page]) => (
                        <Route path={path} component={dynamicPage} />
                    ))}
            </Switch>
        </App>
    </Router>
);
