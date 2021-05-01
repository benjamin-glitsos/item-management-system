import ListPage from "../pages/list";
import searchTest from "../tests/search";

describe("Items Page", () => {
    const slug = "items";
    const list = new ListPage(slug);

    beforeEach(() => {
        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.forEach(item =>
                cy.request(
                    "POST",
                    Cypress.env("API_BASE_URL") + "/api/rest/v1/items/",
                    item
                )
            );
        });
    });

    afterEach(() => {
        cy.fixture("dummy-items").then(dummyItems => {
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

    searchTest(list);
});
