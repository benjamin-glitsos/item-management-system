import Page from "./page";

export default class ListPage extends Page {
    constructor({ slug, searchableAttributes }) {
        super({ slug });
        this.searchableAttributes = searchableAttributes;
    }

    searchBar() {
        return cy.get("[data-testid=search-bar]");
    }
}
