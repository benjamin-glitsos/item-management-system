export default class Page {
    constructor(slug) {
        this.slug = slug;
        this.path = `/${slug}`;
        this.apiUrl = `${Cypress.env("API_BASE_URL")}/v1/${this.slug}/`;
    }

    title(s) {
        return `${this.path} : ${s}`;
    }

    visit() {
        cy.visit(this.path);
    }

    create(body) {
        cy.request("POST", this.apiUrl, body);
    }

    delete(body) {
        cy.request("DELETE", this.apiUrl, body);
    }

    createDummyData() {
        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.forEach(item => this.create(item));
        });
    }

    deleteDummyData() {
        cy.fixture("dummy-items").then(dummyItems => {
            this.delete({
                method: "hard",
                keys: dummyItems.map(item => item.key)
            });
        });
    }

    beforeTest() {
        this.visit(this.slug);
        this.createDummyData();
    }

    afterTest() {
        this.deleteDummyData();
        this.visit(this.slug);
    }
}
