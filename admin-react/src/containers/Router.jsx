import { Router, Route, Switch } from "react-router";
import createBrowserHistory from "history/createBrowserHistory";
import App from "@/presenters/App";
import ReadmePage from "@/presenters/ReadmePage";
import UsersPage from "@/presenters/UsersPage";

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
