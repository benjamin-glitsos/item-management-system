import { useFlags } from "@atlaskit/flag";
import Error from "@atlaskit/icon/glyph/editor/warning";

export default ({ id, error }) => {
    const { showFlag } = useFlags();
    const { title, description } = error;
    return showFlag({
        icon: <Error />,
        id,
        key: `Toast/Error/${error.code}/${id}`,
        title,
        description
    });
};
