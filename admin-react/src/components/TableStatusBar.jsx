import { useContext } from "react";
import { Fragment } from "react";
import NonBreakingSpace from "%/components/NonBreakingSpace";
import numbersToWords from "number-to-words";
import { ListContext } from "%/components/List/List";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";
import isBlank from "%/utilities/isBlank";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const numberOfSelected = context.state.selected.length;
    const isSearch = isBlank(context.query.search);
    const nameSingular = context.nameSingular;
    const namePlural = context.namePlural;
    const stats = context.state.response.data;
    const totalItemsCount = stats.total_items_count;
    const filteredItemsCount = stats.filtered_items_count;
    const pageItemsStart = stats.page_items_start;
    const pageItemsEnd = stats.page_items_end;

    const namePluralOrMatches = !isSearch ? namePlural : "matches";

    if (isLoading) {
        return <NonBreakingSpace />;
    } else if ([undefined, null, 0].includes(totalItemsCount)) {
        return `Zero ${namePluralOrMatches}.`;
    } else if (numberOfSelected === 0) {
        const filteredStatus = `Showing ${namePluralOrMatches} ${
            pageItemsStart + String.fromCharCode(8211) + pageItemsEnd
        } of ${filteredItemsCount}`;
        const totalStatus =
            filteredItemsCount < totalItemsCount && `out of ${totalItemsCount}`;
        return [filteredStatus, totalStatus].filter(x => !!x).join(" ") + ".";
    } else if (numberOfSelected < 10) {
        const numberToWorded = n =>
            capitaliseFirstLetter(numbersToWords.toWords(n));
        return `${numberToWorded(numberOfSelected)} ${
            numberOfSelected === 1 ? nameSingular : namePlural
        } selected.`;
    } else {
        return `${numberOfSelected} selected.`;
    }
};
