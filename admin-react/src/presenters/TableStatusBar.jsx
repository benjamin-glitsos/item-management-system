import { Fragment } from "react";
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
    if (numberOfSelected === 0) {
        return (
            <Fragment>
                Showing items {itemRangeStart}
                &ndash;{itemRangeEnd} of total {totalItemsCount}.
            </Fragment>
        );
    } else {
        return (
            <Fragment>
                {capitaliseFirstLetter(
                    numbersToWords.toWords(numberOfSelected)
                )}{" "}
                items selected.
            </Fragment>
        );
    }
};
