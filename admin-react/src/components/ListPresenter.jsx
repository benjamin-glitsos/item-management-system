import { useContext } from "react";
import { titleCase } from "title-case";
import AtlaskitDynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import FullwidthLayout from "%/components/FullwidthLayout";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import TableStatusBar from "%/components/TableStatusBar";
import NoData from "%/components/NoData";
import ActionsBar from "%/components/ActionsBar";
import Pagination from "%/components/Pagination";
import { ListContext } from "%/components/List";
import PageLayout from "%/components/PageLayout";
import config from "%/config";
import styled from "styled-components";

export default () => {
    const context = useContext(ListContext);
    const sort = context.query.sort || config.defaultSort;

    return (
        <PageLayout title={context.metaTitle} description={context.description}>
            <FullwidthLayout>
                <PageHeader
                    breadcrumbs={
                        <BreadcrumbBar
                            breadcrumbs={[
                                context.homeBreadcrumb,
                                [
                                    titleCase(context.namePlural),
                                    `./${context.namePlural}`
                                ]
                            ]}
                        />
                    }
                    actions={<ActionsBar />}
                    bottomBar={<TableStatusBar />}
                >
                    {context.title}
                </PageHeader>
                <DynamicTable
                    head={context.head}
                    rows={context.rows}
                    isLoading={context.state.isLoading}
                    emptyView={<NoData />}
                    sortKey={sort[0]}
                    sortOrder={sort[1]}
                    defaultSortKey="created_at"
                    defaultSortOrder="DESC"
                    onSort={e => {
                        context.setDeselectAll();
                        context.setSort([e.key, e.sortOrder]);
                    }}
                />
                <Pagination />
            </FullwidthLayout>
        </PageLayout>
    );
};

const DynamicTable = styled(AtlaskitDynamicTable)`
    overflow-y: auto;
    margin-bottom: 22px;

    & > div {
        min-width: 800px;
        margin-bottom: 2px;
    }
`;
