import { useEffect } from "react";
import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useImmer } from "use-immer";
import axios from "axios";
import { useFlags } from "@atlaskit/flag";
import formatDate from "%/utilities/formatDate";
import toast from "%/utilities/toast";
import config from "%/config";

export default ({
    apiPath,
    nameSingular,
    namePlural,
    keyColumnSingular,
    keyColumnPlural
}) => {
    const history = useHistory();

    const apiUrl = config.serverUrl + apiPath;

    const [state, setState] = useImmer({});

    const { showFlag } = useFlags();

    return {
        state
    };
};
