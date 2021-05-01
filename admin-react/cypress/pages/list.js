export default class ListPage {
    constructor(slug) {
        this.slug = slug;
        this.path = `/${slug}`;
    }

    title(s) {
        return `${this.path} : ${s}`;
    }

    visit(slug) {
        cy.visit(this.path);
    }

    searchBar() {
        return cy.get("[data-testid=search-bar]");
    }
}
