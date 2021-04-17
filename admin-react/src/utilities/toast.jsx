import Error from "@atlaskit/icon/glyph/editor/warning";

export default (id, error, showFlag) =>
    showFlag({
        icon: <Error />,
        id,
        key: `Toast/Error/${error.code},${id}`,
        title: error.title,
        description: error.description,
        isAutoDismiss: true
    });
