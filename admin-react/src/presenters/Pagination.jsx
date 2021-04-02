import { useContext } from "react";
import styled from "styled-components";
import HorizontalRule from "%/presenters/HorizontalRule";
import Pagination from "@atlaskit/pagination";
import enumerate from "%/utilities/enumerate";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const setPageNumber = context.setPageNumber;

    const stats = context.state.response.data;
    const pageNumber = stats.page_number;
    const filteredPagesCount = stats.filtered_pages_count;
    const pageItemsCount = stats.page_items_count;

    if (!isLoading && filteredPagesCount > 0 && pageItemsCount > 0) {
        return (
            <PaginationStyles>
                <Pagination
                    pages={enumerate(filteredPagesCount)}
                    selectedIndex={pageNumber - 1}
                    onChange={(event, pageNumber, analyticsEvent) =>
                        setPageNumber(pageNumber)
                    }
                />
            </PaginationStyles>
        );
    } else if (isLoading) {
        return null;
    } else {
        return (
            <HorizontalRuleStyles>
                <HorizontalRule />
            </HorizontalRuleStyles>
        );
    }
};

const PaginationStyles = styled.div`
    text-align: center;
    & > div {
        display: inline-flex !important;
    }
`;

const HorizontalRuleStyles = styled.div`
    margin-top: 24px;
`;
