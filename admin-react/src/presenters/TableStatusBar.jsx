import { useContext } from "react";
import { Fragment } from "react";
import NonBreakingSpace from "%/presenters/NonBreakingSpace";
import numbersToWords from "number-to-words";
import simplur from "simplur";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const numberOfSelected = context.state.selected.length;

    const stats = context.state.response.data;
    const totalItemsCount = stats.total_items_count;
    const filteredItemsCount = stats.filtered_items_count;
    const pageItemsStart = stats.page_items_start;
    const pageItemsEnd = stats.page_items_end;

    if (isLoading) {
        return <NonBreakingSpace />;
    } else if (totalItemsCount === undefined || totalItemsCount === null) {
        return "Zero items.";
    } else if (numberOfSelected === 0) {
        return (
            <Fragment>
                Showing items {pageItemsStart}
                &ndash;{pageItemsEnd} of {filteredItemsCount}.
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
