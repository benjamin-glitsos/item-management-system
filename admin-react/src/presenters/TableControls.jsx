import styled from "styled-components";
import HorizontalRule from "%/presenters/HorizontalRule";
import { Grid, GridColumn } from "@atlaskit/page";
import Pagination from "@atlaskit/pagination";
import Select from "@atlaskit/select";
import enumerate from "%/utilities/enumerate";

export default ({
    isLoading,
    totalPages,
    pageNumber,
    pageLength,
    setPageNumber,
    setPageLength
}) => {
    if (!isLoading && pageLength > 0 && totalPages > 0) {
        return (
            <TableControlStyles>
                <Grid>
                    <GridColumn medium={1}></GridColumn>
                    <GridColumn medium={10}>
                        <PageNumberStyles>
                            <Pagination
                                pages={enumerate(totalPages)}
                                selectedIndex={pageNumber - 1}
                                onChange={setPageNumber}
                            />
                        </PageNumberStyles>
                    </GridColumn>
                    <GridColumn medium={1}>
                        <PageLengthStyles>
                            <Select
                                options={[10, 25, 50, 100].map(n => ({
                                    label: n,
                                    value: n
                                }))}
                                value={pageLength}
                                placeholder={`${pageLength} per page`}
                                onChange={setPageLength}
                            />
                        </PageLengthStyles>
                    </GridColumn>
                </Grid>
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

const PageNumberStyles = styled.div`
    & > div {
        justify-content: center;
    }
`;

const PageLengthStyles = styled.div`
    & > div {
        justify-content: right;
        margin-right: -8px;
    }
`;

const HorizontalRuleStyles = styled.div`
    margin-top: 24px;
`;
