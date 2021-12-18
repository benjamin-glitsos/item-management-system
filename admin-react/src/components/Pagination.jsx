import { useContext } from "react";
import styled from "styled-components";
import Pagination from "@atlaskit/pagination";
import enumerate from "%/utilities/enumerate";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const setPageNumber = context.setPageNumber;
    const setDeselectAll = context.setDeselectAll;

    const stats = context.state.response.data;
    const pageNumber = (context.query.page_number || 1) - 1;
    const filteredPagesCount = stats.filtered_pages_count;
    const pageItemsCount = stats.page_items_count;

    if (!isLoading && filteredPagesCount > 0 && pageItemsCount > 0) {
        return (
            <Styles>
                <Pagination
                    pages={enumerate(filteredPagesCount)}
                    selectedIndex={pageNumber}
                    onChange={(event, pageNumber, analyticsEvent) => {
                        setDeselectAll();
                        setPageNumber(pageNumber);
                    }}
                />
            </Styles>
        );
    } else {
        return null;
    }
};

const Styles = styled.div`
    text-align: center;
    & > div {
        display: inline-flex !important;
    }
`;
