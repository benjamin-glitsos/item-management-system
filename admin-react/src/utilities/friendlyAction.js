export default (numberOfItems, title) => {
    if (numberOfItems < 1) {
        return "Zero items selected";
    } else if (numberOfItems == 1) {
        return title;
    } else if (numberOfItems > 1) {
        return `${title} (${numberOfItems} items)`;
    }
};
