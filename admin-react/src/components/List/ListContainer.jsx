import { useEffect } from "react";
import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useImmer } from "use-immer";
import useThrottledEffect from "use-throttled-effect";
import { useQueryParams, NumberParam, StringParam } from "use-query-params";
import axios from "axios";
import { useFlags } from "@atlaskit/flag";
import { Checkbox } from "@atlaskit/checkbox";
import Message from "@atlaskit/icon/glyph/editor/warning";
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
import toast from "%/utilities/toast";
import config from "%/config";

export default ({
    apiPath,
    defaultState,
    headContentColumns,
    rowTransform = row => row,
    nameSingular,
    namePlural,
    keyColumnSingular,
    keyColumnPlural
}) => {
    const history = useHistory();

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

    const requestDeleteItems = (method, keys) =>
        axios({
            method: "DELETE",
            url: apiUrl,
            data: {
                method,
                [keyColumnPlural]: keys
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

    const setDeselectAll = () =>
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
            toast("error", i, error, showFlag);
        });

    const deleteItemsAction = async (method, keys) => {
        await requestDeleteItems(method, keys);
        listItemsAction();
        setDeselectAll();
    };

    useEffect(listItemsAction, []);

    useThrottledEffect(listItemsAction, 500, [query, state.request]);

    useEffect(handleErrorsAction, [state.response.errors]);

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
                row => [
                    <Checkbox
                        isChecked={state.selected.includes(key)}
                        onChange={() => setSelected(key)}
                    />,
                    ...Object.values(row),
                    ActionsMenu({
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
                            }
                        ]
                    })
                ],
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
