export default class ListPage {
    visit(slug) {
        cy.visit(`/${slug}`);
    }
}
