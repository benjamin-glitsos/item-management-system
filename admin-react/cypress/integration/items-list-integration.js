import ListPage from "../pages/list";
import searchTest from "../tests/search";

describe("Items List Integration Testing", () => {
    const list = new ListPage("items");

    beforeEach(() => list.beforeTest());
    afterEach(() => list.afterTest());

    searchTest(list);
});
