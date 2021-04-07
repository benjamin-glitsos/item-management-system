import { useContext } from "react";
import { Fragment } from "react";
import NonBreakingSpace from "%/presenters/NonBreakingSpace";
import numbersToWords from "number-to-words";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const numberOfSelected = context.state.selected.length;

    const nameSingular = context.nameSingular;
    const namePlural = context.namePlural;
    const stats = context.state.response.data;
    const totalItemsCount = stats.total_items_count;
    const filteredItemsCount = stats.filtered_items_count;
    const pageItemsStart = stats.page_items_start;
    const pageItemsEnd = stats.page_items_end;

    if (isLoading) {
        return <NonBreakingSpace />;
    } else if (totalItemsCount === undefined || totalItemsCount === null) {
        return `Zero ${namePlural}.`;
    } else if (numberOfSelected === 0) {
        const filteredStatus = `Showing ${namePlural} ${
            pageItemsStart + String.fromCharCode(8211) + pageItemsEnd
        } of ${filteredItemsCount}`;
        const totalStatus =
            filteredItemsCount < totalItemsCount && `out of ${totalItemsCount}`;
        return [filteredStatus, totalStatus].filter(x => !!x).join(" ") + ".";
    } else {
        const numberToWorded = n =>
            capitaliseFirstLetter(numbersToWords.toWords(n));
        return (
            <Fragment>
                {numberOfSelected === 1 ? nameSingular : namePlural} selected.
            </Fragment>
        );
    }
};
