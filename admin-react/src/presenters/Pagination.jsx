import { useContext } from "react";
import styled from "styled-components";
import HorizontalRule from "%/presenters/HorizontalRule";
import Pagination from "@atlaskit/pagination";
import enumerate from "%/utilities/enumerate";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isLoading = context.state.isLoading;
    const pageNumber = context.state.response.data.page_number;
    const pageLength = context.state.response.data.page_length;
    const totalPages = context.state.response.data.total_pages;
    const setPageNumber = context.setPageNumber;

    if (!isLoading && totalPages > 0) {
        return (
            <PaginationStyles>
                <Pagination
                    pages={enumerate(totalPages)}
                    selectedIndex={pageNumber - 1}
                    onChange={(event, pageNumber, analyticsEvent) =>
                        setPageNumber(pageNumber)
                    }
                />
            </PaginationStyles>
        );
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
