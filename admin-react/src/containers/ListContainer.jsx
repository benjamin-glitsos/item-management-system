import { useEffect } from "react";
import { useImmer } from "use-immer";
import axios from "axios";
import config from "%/config";
import TableActionsMenu from "%/presenters/TableActionsMenu";
import { useFlags } from "@atlaskit/flag";
import Error from "@atlaskit/icon/glyph/editor/warning";

export default ({ apiPath, defaultState, head: _head, rows: _rows }) => {
    const apiUrl = config.serverUrl + apiPath;

    const [state, setState] = useImmer(defaultState);

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
            draft.response = Object.assign(draft.response, response.data);
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

    const setPageNumber = (event, page, analyticsEvent) =>
        setState(draft => {
            draft.request.body.page_number = page;
        });

    const setPageLength = selectedOption =>
        setState(draft => {
            draft.request.body.page_number = 1;
            draft.request.body.page_length = selectedOption.value;
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
                const response = await requestListItems(state.request.body);
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

    useEffect(listItemsAction, [state.request]);

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
