export default class Page {
    constructor({ slug }) {
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

    createDummyData(max) {
        cy.fixture("dummy-items").then(dummyItems => {
            dummyItems.take(max).forEach(item => this.create(item));
        });
    }

    deleteDummyData(max) {
        cy.fixture("dummy-items").then(dummyItems => {
            this.delete({
                method: "hard",
                keys: dummyItems.take(max).map(item => item.key)
            });
        });
    }
}
