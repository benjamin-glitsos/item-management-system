import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Exists", () => {
        list.visit("users");

        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.forEach(item =>
                cy.request(
                    "POST",
                    Cypress.config("apiBaseUrl") + "/api/rest/v1/items/",
                    item
                )
            );

            list.searchBar().type(dummyItems[0]);
        });
    });
});
