import ListPage from "../pages/list";

describe("Search List", () => {
    const list = new ListPage();

    it("Exists", () => {
        list.visit("users");
        cy.get("[data-testid=search-bar]").as("search-bar");
        cy.get("@search-bar").type("benglitsos");
    });
});
