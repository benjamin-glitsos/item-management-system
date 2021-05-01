export default page => {
    describe(page.title("Search"), () => {
        it(page.title("Search bar exists"), () => {
            cy.fixture("dummy-items").then(dummyItems => {
                const firstDummyItem = dummyItems[0];

                page.searchBar().type(firstDummyItem.key);
                page.searchBar().clear();
                page.searchBar().type(firstDummyItem.name);
                page.searchBar().clear();
            });
        });
    });
};
