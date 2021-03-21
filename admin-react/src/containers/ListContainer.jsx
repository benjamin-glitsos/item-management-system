import { useEffect } from "react";
import { useImmer } from "use-immer";
import { useQueryParams, NumberParam } from "use-query-params";
import axios from "axios";
import TableActionsMenu from "%/presenters/TableActionsMenu";
import { useFlags } from "@atlaskit/flag";
import Error from "@atlaskit/icon/glyph/editor/warning";
import config from "%/config";

export default ({ apiPath, defaultState, head: _head, rows: _rows }) => {
    const apiUrl = config.serverUrl + apiPath;

    const [state, setState] = useImmer(defaultState);

    const [query, setQuery] = useQueryParams({
        page_number: NumberParam,
        page_length: NumberParam
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

    const queryPageNumber = pageNumber => ({
        page_number: pageNumber === 1 ? undefined : pageNumber
    });

    const queryPageLength = pageLength => ({
        page_length:
            pageLength === config.defaultPageLength ? undefined : pageLength
    });

    const setPageNumber = (event, pageNumber, analyticsEvent) =>
        setQuery(queryPageNumber(pageNumber));

    const setPageLength = selectedOption =>
        setQuery({
            ...queryPageLength(selectedOption.value),
            ...queryPageNumber(1)
        });

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
    useEffect(listItemsAction, [query, state.request]);
    useEffect(handleErrorsAction, [state.response.errors]);

    const head = _head(setRemoveAllSelected, state.selected);

    const rows = state.response.data.items.map((row, i) =>
        _rows(row, i, state.selected, setSelected, () =>
            TableActionsMenu({
                softDeleteAction: () => deleteItemsAction("soft", [row[0]]),
                hardDeleteAction: () => deleteItemsAction("hard", [row[0]])
            })
        )
    );

    const doesDataExist =
        !state.isLoading && state.response.data.total_pages > 0;

    return {
        doesDataExist,
        head,
        rows,
        state,
        setPageNumber,
        setPageLength,
        deleteItemsAction
    };
};
