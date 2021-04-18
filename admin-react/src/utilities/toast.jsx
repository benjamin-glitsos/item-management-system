import ErrorIcon from "@atlaskit/icon/glyph/editor/warning";
import SuccessIcon from "@atlaskit/icon/glyph/editor/success";
import InfoIcon from "@atlaskit/icon/glyph/editor/info";

export default (type, id, error, showFlag) => {
    const icon = (() => {
        switch (type) {
            case "error":
                return <ErrorIcon />;
            case "success":
                return <SuccessIcon />;
            case "info":
                return <InfoIcon />;
            default:
                return null;
        }
    })();

    showFlag({
        icon,
        id,
        key: `Toast/Message/${error.code},${id}`,
        title: error.title,
        description: error.description,
        isAutoDismiss: true
    });
};
