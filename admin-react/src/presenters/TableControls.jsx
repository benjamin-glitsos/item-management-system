import styled from "styled-components";
import HorizontalRule from "%/presenters/HorizontalRule";
import Pagination from "@atlaskit/pagination";
import enumerate from "%/utilities/enumerate";

export default ({ isLoading, totalPages, pageNumber, setPageNumber }) => {
    if (!isLoading && totalPages > 0) {
        return (
            <TableControlStyles>
                <Pagination
                    pages={enumerate(totalPages)}
                    selectedIndex={pageNumber - 1}
                    onChange={setPageNumber}
                />
            </TableControlStyles>
        );
    } else {
        return (
            <HorizontalRuleStyles>
                <HorizontalRule />
            </HorizontalRuleStyles>
        );
    }
};

const TableControlStyles = styled.div`
    & > div {
        max-width: none;
        padding-left: 0;
        padding-right: 0;
    }
`;

const HorizontalRuleStyles = styled.div`
    margin-top: 24px;
`;
