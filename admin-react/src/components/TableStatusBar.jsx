import { useContext } from "react";
import NonBreakingSpace from "%/components/NonBreakingSpace";
import { ListContext } from "%/components/List";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";
import isBlank from "%/utilities/isBlank";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const numberOfSelected = context.state.selected.length;
    const stats = context.state.response.data;
    const totalItemsCount = stats.total_items_count;
    const filteredItemsCount = stats.filtered_items_count;
    const pageItemsStart = stats.page_items_start;
    const pageItemsEnd = stats.page_items_end;

    const name = (() => {
        const nameSingular = context.nameSingular;
        const namePlural = context.namePlural;
        const matchSingular = "match";
        const matchPlural = "matches";

        const isSearch = isBlank(context.query.search);
        const isSingular = numberOfSelected === 1;

        if (isSearch) {
            return isSingular ? matchSingular : matchPlural;
        } else {
            return isSingular ? nameSingular : namePlural;
        }
    })();

    if (isLoading) {
        return <NonBreakingSpace />;
    } else if ([undefined, null, 0].includes(totalItemsCount)) {
        return `Zero ${name}.`;
    } else if (numberOfSelected === 0) {
        const filteredStatus = `Showing ${name} ${
            pageItemsStart + String.fromCharCode(8211) + pageItemsEnd
        } of ${filteredItemsCount}`;
        const totalStatus =
            filteredItemsCount < totalItemsCount && `out of ${totalItemsCount}`;
        return [filteredStatus, totalStatus].filter(x => !!x).join(" ") + ".";
    } else {
        return `${numberOfSelected} ${name} selected.`;
    }
};
