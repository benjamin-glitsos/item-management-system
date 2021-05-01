import ListPage from "../pages/list";
import searchTest from "../tests/search";

describe("Items List E2E Testing", () => {
    const list = new ListPage("items");

    searchTest(list);
});
