import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Exists", () => {
        cy.fixture("dummy-items").as("dummyItems");

        list.visit("users");

        cy.wait("@dummyItems").then(dummyItems =>
            dummyItems.forEach(item =>
                cy.request("POST", Cypress.config("baseUrl"), item)
            )
        );

        cy.wait("@dummyItems").then(dummyItems => {
            cy.get("[data-testid=search-bar]").as("searchBar");
            cy.get("@searchBar").type(dummyItems[0]);
        });
    });
});
