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
    const itemRangeStart = context.state.response.data.range_start;
    const itemRangeEnd = context.state.response.data.range_end;
    const totalItemsCount = context.state.response.data.total_items;
    const numberOfSelected = context.state.selected.length;

    if (isLoading) {
        return <NonBreakingSpace />;
    } else if (totalItemsCount === undefined || totalItemsCount === null) {
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
