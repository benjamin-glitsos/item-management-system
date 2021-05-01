import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Search bar exists", () => {
        list.visit("items");

        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.forEach(item =>
                cy.request(
                    "POST",
                    Cypress.env("API_BASE_URL") + "/api/rest/v1/items/",
                    item
                )
            );

            const firstDummyItem = dummyItems[0];

            list.searchBar().type(firstDummyItem.key);
            list.searchBar().clear();
            list.searchBar().type(firstDummyItem.name);
            list.searchBar().clear();

            cy.request(
                "DELETE",
                Cypress.env("API_BASE_URL") + "/api/rest/v1/items/",
                {
                    method: "hard",
                    keys: dummyItems.map(item => item.key)
                }
            );
        });
    });
});
