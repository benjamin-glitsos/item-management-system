import numbersToWords from "number-to-words";
import fromMaybe from "%/utilities/fromMaybe";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";

export default ({
    currentPage,
    pageLength,
    totalPages,
    itemRangeStart,
    itemRangeEnd,
    totalItemsCount,
    numberOfSelected
}) => {
    if (
        [
            currentPage,
            pageLength,
            totalPages,
            itemRangeStart,
            itemRangeEnd,
            totalItemsCount,
            numberOfSelected
        ].some(x => x)
    ) {
        return "";
    } else if (numberOfSelected === 0) {
        return `Showing page ${currentPage} of ${totalPages} total pages, and items ${itemRangeStart} to ${itemRangeEnd} of ${totalItemsCount} total items, with ${pageLength} items per page.`;
    } else {
        return `${capitaliseFirstLetter(
            numbersToWords.toWords(numberOfSelected)
        )} selected.`;
    }
};
