import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Search bar exists", () => {
        list.visit("users");

        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.forEach(item =>
                cy.request(
                    "POST",
                    Cypress.env("API_BASE_URL") + "/api/rest/v1/items/",
                    item
                )
            );

            list.searchBar().type(dummyItems[0]);
        });
    });
});
