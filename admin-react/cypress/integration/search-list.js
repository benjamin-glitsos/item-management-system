import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Exists", () => {
        cy.fixture("dummy-items").then(dummyItems => {
            list.visit("users");

            dummyItems.forEach(item =>
                cy.request(
                    "POST",
                    Cypress.config("baseUrl") + "/api/rest/v1/items/",
                    item
                )
            );

            list.searchBar().type(dummyItems[0]);
        });
    });
});
