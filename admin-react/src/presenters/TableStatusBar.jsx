import { Fragment } from "react";
import numbersToWords from "number-to-words";
import simplur from "simplur";
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
    if (totalItemsCount === undefined || totalItemsCount === null) {
        return "Zero items.";
    } else if (numberOfSelected === 0) {
        return (
            <Fragment>
                Showing items {itemRangeStart}
                &ndash;{itemRangeEnd} of total {totalItemsCount}.
            </Fragment>
        );
    } else {
        const numberToWorded = n =>
            capitaliseFirstLetter(numbersToWords.toWords(n));
        return (
            <Fragment>
                {simplur`${[
                    numberOfSelected,
                    numberToWorded
                ]} item[|s] selected.`}
            </Fragment>
        );
    }
};
