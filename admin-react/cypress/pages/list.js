import Page from "./page";

export default class ListPage extends Page {
    constructor(slug) {
        super(slug);
    }

    searchBar() {
        return cy.get("[data-testid=search-bar]");
    }
}
