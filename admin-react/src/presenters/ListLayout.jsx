import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import FullwidthLayout from "%/presenters/FullwidthLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import TableStatusBar from "%/presenters/TableStatusBar";
import NoData from "%/presenters/NoData";
import ActionsBar from "%/presenters/ActionsBar";
import Pagination from "%/presenters/Pagination";

export default ({
    title,
    head,
    rows,
    doesDataExist,
    state,
    setPageNumber,
    setPageLength,
    deleteItemsAction,
    breadcrumbs
}) => (
    <FullwidthLayout>
        <PageHeader
            breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
            actions={
                <ActionsBar
                    doesDataExist={doesDataExist}
                    isDeletable={state.selected.length > 0}
                    softDeleteAction={() =>
                        deleteItemsAction("soft", state.selected)
                    }
                    hardDeleteAction={() =>
                        deleteItemsAction("hard", state.selected)
                    }
                    pageLength={state.response.data.page_length}
                    totalPages={state.response.data.total_pages}
                    setPageLength={setPageLength}
                />
            }
            bottomBar={
                <TableStatusBar
                    isLoading={state.isLoading}
                    currentPage={state.response.data.page_number}
                    totalPages={state.response.data.total_pages}
                    itemRangeStart={state.response.data.range_start}
                    itemRangeEnd={state.response.data.range_end}
                    totalItemsCount={state.response.data.total_items}
                    numberOfSelected={state.selected.length}
                />
            }
        >
            {title}
        </PageHeader>
        <DynamicTable
            head={head}
            rows={rows}
            isLoading={state.isLoading}
            emptyView={<NoData />}
        />
        <Pagination
            isLoading={state.isLoading}
            pageNumber={state.response.data.page_number}
            pageLength={state.response.data.page_length}
            totalPages={state.response.data.total_pages}
            setPageNumber={setPageNumber}
        />
    </FullwidthLayout>
);
