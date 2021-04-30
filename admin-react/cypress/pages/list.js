export default class ListPage {
    visit(slug) {
        cy.visit(`/${slug}`);
    }

    searchBar() {
        return cy.get("[data-testid=search-bar]");
    }
}
