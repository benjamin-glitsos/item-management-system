import DynamicTable from "@atlaskit/dynamic-table";
import Pagination from "@atlaskit/pagination";
import PageHeader from "@atlaskit/page-header";
import FullwidthLayout from "%/presenters/FullwidthLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import TableStatusBar from "%/presenters/TableStatusBar";
import NoData from "%/presenters/NoData";
import ActionsBar from "%/presenters/ActionsBar";

export default ({ state, setState, head, rows }) => (
    <FullwidthLayout>
        <PageHeader
            breadcrumbs={
                <BreadcrumbBar
                    breadcrumbs={[
                        ["Home", "/"],
                        ["Users", "/users"]
                    ]}
                />
            }
            actions={<ActionsBar />}
            bottomBar={
                <TableStatusBar
                    currentPage={state.request.data.page_number}
                    pageLength={state.request.data.page_length}
                    totalPages={state.response.data.total_pages}
                    itemRangeStart={state.response.data.range_start}
                    itemRangeEnd={state.response.data.range_end}
                    totalItemsCount={state.response.data.total_items}
                    numberOfSelected={state.selected.length}
                />
            }
        >
            Users
        </PageHeader>
        <DynamicTable
            head={head}
            rows={rows}
            isLoading={state.isLoading}
            emptyView={<NoData />}
        />
        <Pagination
            pages={[...Array(7).keys()].map(n => n + 1)}
            onChange={(event, page, analyticsEvent) =>
                setState(draft => {
                    draft.request.data.page_number = page;
                })
            }
        />
    </FullwidthLayout>
);
// rowsPerPage={state.request.data.page_length}
// defaultPage={state.request.data.page_number}
// onSetPage={() => {}}
