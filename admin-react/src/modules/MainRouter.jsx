import { Router, Route, Switch } from "react-router";
import createBrowserHistory from "history/createBrowserHistory";
import App from "./App";
import ReadmePage from "../pages/ReadmePage";
import UsersPage from "../pages/UsersPage";

export default () => (
    <Router history={createBrowserHistory()}>
        <App>
            <Switch>
                <Route exact path="/" component={ReadmePage} />
                <Route path="/users" component={UsersPage} />
            </Switch>
        </App>
    </Router>
);
