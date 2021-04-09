import { useEffect } from "react";
import { useImmer } from "use-immer";
import useThrottledEffect from "use-throttled-effect";
import { useQueryParams, NumberParam, StringParam } from "use-query-params";
import axios from "axios";
import pipe from "pipe-functions";
import { useFlags } from "@atlaskit/flag";
import { Checkbox } from "@atlaskit/checkbox";
import Error from "@atlaskit/icon/glyph/editor/warning";
import ActionsMenu from "%/presenters/ActionsMenu";
import {
    queryPageNumber,
    queryPageLength,
    querySearch,
    querySort
} from "%/utilities/queryParameters";
import RemoveAllSelected from "%/presenters/RemoveAllSelected";
import { CommaArrayParam } from "%/utilities/commaArrayQueryParameter";
import config from "%/config";

export default ({ apiPath, defaultState, headContentColumns, rowsTransform }) => {
    const apiUrl = config.serverUrl + apiPath;

    const [state, setState] = useImmer(defaultState);

    const [query, setQuery] = useQueryParams({
        page_number: NumberParam,
        page_length: NumberParam,
        search: StringParam,
        sort: CommaArrayParam
    });

    const { showFlag } = useFlags();

    const requestListItems = body =>
        axios({
            method: "REPORT",
            url: apiUrl,
            data: body
        });

    const requestDeleteItems = (method, usernames) =>
        axios({
            method: "DELETE",
            url: apiUrl,
            data: {
                method,
                usernames
            }
        });

    const setLoading = bool =>
        setState(draft => {
            draft.isLoading = bool;
        });

    const setResponse = response =>
        setState(draft => {
            Object.assign(draft.response, response.data);
        });

    const setResponseErrors = errors => {
        const list = errors?.response?.data?.errors;
        if (list) {
            setState(draft => {
                draft.response.errors = list;
            });
        } else {
            console.error(errors);
        }
    };

    const setPageNumber = (pageNumber = 1) =>
        setQuery({ page_number: queryPageNumber(pageNumber) });

    const setPageLength = (pageLength = config.defaultPageLength) =>
        setQuery({
            page_number: queryPageNumber(1),
            page_length: queryPageLength(pageLength)
        });

    const setSearch = (search = "") =>
        setQuery({
            page_number: queryPageNumber(1),
            search: querySearch(search)
        });

    const setSort = (sort = []) =>
        setQuery({ sort: querySort(sort.slice(0, 2)) });

    const setSelected = key =>
        setState(draft => {
            if (!draft.selected.includes(key)) {
                draft.selected.push(key);
            } else {
                draft.selected = draft.selected.filter(x => x !== key);
            }
        });

    const setRemoveAllSelected = () =>
        setState(draft => {
            draft.selected = [];
        });

    const listItemsAction = () => {
        (async () => {
            setLoading(true);
            try {
                const response = await requestListItems({
                    ...state.request.body,
                    ...query
                });
                setResponse(response);
            } catch (error) {
                setResponseErrors(error);
            }
            setLoading(false);
        })();
    };

    const handleErrorsAction = () =>
        state.response.errors.forEach((error, i) => {
            console.error(error);
            showFlag({
                icon: <Error />,
                id: i,
                key: `Toast/Error/${error.code}/${i}`,
                title: error.title,
                description: error.description,
                isAutoDismiss: true
            });
        });

    const deleteItemsAction = async (method, usernames) => {
        await requestDeleteItems(method, usernames);
        listItemsAction();
        setRemoveAllSelected();
    };

    useEffect(listItemsAction, []);

    useThrottledEffect(listItemsAction, 500, [query, state.request]);

    useEffect(handleErrorsAction, [state.response.errors]);

    const selectionHeadColumn = {
        key: "isSelected",
        content: (
            <RemoveAllSelected
                doesSelectionExist={state.selected.length > 0}
                action={setRemoveAllSelected}
            />
        ),
        isSortable: false,
        width: 3
    };

    const metaHeadColumns = [
        {
            key: "created_at",
            content: "Created At",
            isSortable: true,
            width: 15
        },
        {
            key: "edited_at",
            content: "Edited At",
            isSortable: true,
            width: 15
        }
    ];

    const actionsHeadColumn = {
        key: "actions",
        content: null,
        isSortable: false,
        width: 10
    };

    const head = {
        cells: [
            selectionHeadColumn,
            ...headContentColumns,
            ...metaHeadColumns,
            actionsHeadColumn
        ]
    };

    const rows = state.response.data.items.map((row, i) => ({
        key: `Table/Row/${row.username}-${i}`,
        cells: pipe(
            row,
            row => [
                <Checkbox
                    isChecked={state.selected.includes(row.username)}
                    onChange={() => setSelected(row.username)}
                />,
                ...Object.values(row),
                ActionsMenu({ items: [row.username] })
            ],
            x =>
                x.map((x, i) => ({
                    key: `Table/Cell/${i}`,
                    content: x
                }))
        )
    }));

    const isDataEmpty =
        state.isLoading || !(state.response.data.total_items_count > 0);

    return {
        isDataEmpty,
        head,
        rows,
        state,
        query,
        setPageNumber,
        setPageLength,
        setSearch,
        setSort,
        setRemoveAllSelected,
        deleteItemsAction
    };
};
