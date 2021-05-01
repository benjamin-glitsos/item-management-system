import ListPage from "./list";

export default class ItemsList extends ListPage {
    constructor() {
        super({ slug: "items", searchableAttributes: ["key", "name"] });
    }
}
