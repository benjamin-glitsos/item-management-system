export default page => {
    describe(page.title("Search"), () => {
        before(() => page.createDummyData(1));
        after(() => page.deleteDummyData(1));

        cy.fixture("dummy-items").then(dummyItems => {
            const firstDummyItem = dummyItems[0];

            it(page.title("Search bar exists"), () => {
                page.searchBar().as("searchBar");
            });

            it(page.title("Search by each searchable attribute"), () => {
                page.searchableAttributes.forEach(attribute => {
                    cy.get("@searchBar").type(firstDummyItem[attribute]);
                    cy.get("@searchBar").clear();
                    // TODO: check here that the dummy item is in the filtered list
                });
            });
        });
    });
};
