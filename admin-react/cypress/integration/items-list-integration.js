import ItemsList from "../pages/items-list";
import searchTest from "../tests/search";

describe("Items List Integration Testing", () => {
    const page = new ItemsList();

    beforeEach(() => page.visit());
    afterEach(() => page.visit());

    searchTest(page);
});
