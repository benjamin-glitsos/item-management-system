export default (numberOfItems, title, namePlural) => {
    if (numberOfItems < 1) {
        return `Zero ${namePlural} selected`;
    } else if (numberOfItems == 1) {
        return title;
    } else if (numberOfItems > 1) {
        return `${title} (${numberOfItems} ${namePlural})`;
    }
};
