import { Fragment } from "react";
import styled from "styled-components";
import { Helmet } from "react-helmet";
import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import FullwidthLayout from "%/presenters/FullwidthLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import TableStatusBar from "%/presenters/TableStatusBar";
import NoData from "%/presenters/NoData";
import ActionsBar from "%/presenters/ActionsBar";
import TableControls from "%/presenters/TableControls";

export default ({
    title,
    description,
    head,
    rows,
    state,
    setPageNumber,
    setPageLength,
    deleteUsersAction
}) => (
    <Fragment>
        <Helmet>
            <title>
                {title} : {process.env.PROJECT_ABBREV || "IMS"}
            </title>
            <meta name="description" content={description} />
        </Helmet>
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
                actions={
                    <ActionsBar
                        isDeletable={state.selected.length > 0}
                        softDeleteAction={() =>
                            deleteUsersAction("soft", state.selected)
                        }
                        hardDeleteAction={() =>
                            deleteUsersAction("hard", state.selected)
                        }
                    />
                }
                bottomBar={
                    <TableStatusBar
                        isLoading={state.isLoading}
                        currentPage={state.request.body.page_number}
                        pageLength={state.request.body.page_length}
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
            <TableControls
                isLoading={state.isLoading}
                totalPages={state.response.data.total_pages}
                pageNumber={state.request.body.page_number}
                pageLength={state.request.body.page_length}
                setPageNumber={setPageNumber}
                setPageLength={setPageLength}
            />
        </FullwidthLayout>
    </Fragment>
);
