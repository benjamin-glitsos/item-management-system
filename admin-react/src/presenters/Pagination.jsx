import styled from "styled-components";
import HorizontalRule from "%/presenters/HorizontalRule";
import Pagination from "@atlaskit/pagination";
import enumerate from "%/utilities/enumerate";

export default ({ isLoading, totalPages, pageNumber, setPageNumber }) => {
    if (!isLoading && totalPages > 0) {
        return (
            <PaginationStyles>
                <Pagination
                    pages={enumerate(totalPages)}
                    selectedIndex={pageNumber - 1}
                    onChange={setPageNumber}
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
