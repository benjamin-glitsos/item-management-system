import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import Pagination from "@atlaskit/pagination";
import Select from "@atlaskit/select";
import FullwidthLayout from "%/presenters/FullwidthLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import TableStatusBar from "%/presenters/TableStatusBar";
import NoData from "%/presenters/NoData";
import ActionsBar from "%/presenters/ActionsBar";
import enumerateToNumber from "%/utilities/enumerateToNumber";

export default ({ head, rows, state, setPageNumber, setPageLength }) => (
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
            pages={enumerateToNumber(state.response.data.total_pages)}
            onChange={setPageNumber}
        />
        <Select
            options={[10, 25, 50, 100].map(n => ({
                label: n,
                value: n
            }))}
            value={state.request.data.page_length}
            placeholder={state.request.data.page_length}
            onChange={setPageLength}
        />
    </FullwidthLayout>
);
