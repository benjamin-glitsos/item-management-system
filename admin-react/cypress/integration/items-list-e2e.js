import ItemsList from "../pages/items-list";
import searchTest from "../tests/search";

describe("Items List E2E Testing", () => {
    const page = new ItemsList();

    searchTest(page);
});
