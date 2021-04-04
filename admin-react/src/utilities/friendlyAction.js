import numbersToWords from "number-to-words";

export default (numberOfItems, title) => {
    if (numberOfItems < 1) {
        return `Cannot ${title} zero items`;
    } else if (numberOfItems === 1) {
        return title;
    } else if (numberOfItems > 1) {
        return `${title} ${numbersToWords.toWords(numberOfItems)} items`;
    }
};
