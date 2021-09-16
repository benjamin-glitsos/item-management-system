import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useImmer } from "use-immer";
import useThrottledEffect from "use-throttled-effect";
import { useQueryParams, NumberParam, StringParam } from "use-query-params";
import { Checkbox } from "@atlaskit/checkbox";
import ActionsMenu from "%/components/ActionsMenu";
import {
    queryPageNumber,
    queryPageLength,
    querySearch,
    querySort
} from "%/utilities/queryParameters";
import RemoveAllSelected from "%/components/RemoveAllSelected";
import { CommaArrayParam } from "%/utilities/commaArrayQueryParameter";
import formatDate from "%/utilities/formatDate";
import { listService, deleteService } from "%/services";
import axiosErrorHandler from "%/utilities/axiosErrorHandler";
import config from "%/config";

export default ({
    headContentColumns,
    rowTransform = row => row,
    nameSingular,
    namePlural,
    keyColumnSingular,
    keyColumnPlural
}) => {
    const history = useHistory();

    const defaultState = {
        request: { body: {} },
        response: { data: { items: [] } },
        isLoading: true,
        selected: []
    };

    const [state, setState] = useImmer(defaultState);

    const [query, setQuery] = useQueryParams({
        page_number: NumberParam,
        page_length: NumberParam,
        search: StringParam,
        sort: CommaArrayParam
    });

    const setLoading = bool =>
        setState(draft => {
            draft.isLoading = bool;
        });

    const setResponse = response =>
        setState(draft => {
            Object.assign(draft.response, response.data);
        });

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
        setQuery({
            page_number: queryPageNumber(1),
            sort: querySort(sort.slice(0, 2))
        });

    const setSelected = key =>
        setState(draft => {
            if (!draft.selected.includes(key)) {
                draft.selected.push(key);
            } else {
                draft.selected = draft.selected.filter(x => x !== key);
            }
        });

    const setDeselectAll = () =>
        setState(draft => {
            draft.selected = [];
        });

    const listItemsAction = async () => {
        setLoading(true);
        await listService({
            path: namePlural,
            params: { ...state.request.body, ...query }
        })
            .then(setResponse)
            .catch(axiosErrorHandler);
        setLoading(false);
    };

    const deleteItemsAction = async (method, keys) => {
        await deleteService({ path: namePlural, method, keys }).catch(
            axiosErrorHandler
        );
        listItemsAction();
        setDeselectAll();
    };

    useThrottledEffect(listItemsAction, 500, [query, state.request]);

    const selectionHeadColumn = {
        key: "isSelected",
        content: (
            <RemoveAllSelected
                doesSelectionExist={state.selected.length > 0}
                action={setDeselectAll}
            />
        ),
        isSortable: false,
        width: 3
    };

    const metaHeadColumns = [
        {
            key: "created_at",
            content: "Created",
            isSortable: true
        },
        {
            key: "edited_at",
            content: "Edited",
            isSortable: true
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

    const rows = state.response.data.items.map((row, i) => {
        const key = row[keyColumnSingular];
        return {
            key: `Row/${key},${i}`,
            cells: R.pipe(
                R.evolve({
                    [keyColumnSingular]: x => (
                        <Link to={`/${namePlural}/${key}`}>{key}</Link>
                    ),
                    created_at: formatDate,
                    edited_at: formatDate
                }),
                rowTransform,
                row => {
                    const isSelected = state.selected.includes(key);
                    const setSelectedHandler = () => setSelected(key);
                    return [
                        <Checkbox
                            isChecked={isSelected}
                            onChange={setSelectedHandler}
                        />,
                        ...Object.values(row),
                        ActionsMenu({
                            namePlural,
                            items: [key],
                            deleteItemsAction,
                            setDeselectAll,
                            additionalItems: [
                                {
                                    title: "Open",
                                    onClick: () => {
                                        history.push(`/${namePlural}/${key}`);
                                    },
                                    isVisible: true
                                },
                                {
                                    title: !isSelected ? "Select" : "Deselect",
                                    onClick: setSelectedHandler,
                                    isVisible: true
                                }
                            ]
                        })
                    ];
                },
                x =>
                    x.map((x, i) => ({
                        key: `Cell/${key},${i}`,
                        content: x
                    }))
            )(row)
        };
    });

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
        setDeselectAll,
        deleteItemsAction
    };
};
