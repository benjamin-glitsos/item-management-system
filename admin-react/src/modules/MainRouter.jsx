import { Router, Route, Switch } from "react-router";
import createBrowserHistory from "history/createBrowserHistory";
import App from "./App";
import ReadmePage from "../pages/ReadmePage";
import UsersPage from "../pages/UsersPage";

export default () => (
    <Router history={createBrowserHistory()}>
        <App>
            <Switch>
                <Route path="/users" component={UsersPage} />
                <Route path="/" component={ReadmePage} />
            </Switch>
        </App>
    </Router>
);
