import { useEffect } from "react";
import R from "ramda";
import { useHistory, Link } from "react-router-dom";
import { useImmer } from "use-immer";
import axios from "axios";
import { useFlags } from "@atlaskit/flag";
import formatDate from "%/utilities/formatDate";
import toast from "%/utilities/toast";
import config from "%/config";

export default ({ apiPath, username }) => {
    const history = useHistory();

    const apiUrl = config.serverUrl + apiPath;

    const [state, setState] = useImmer({});

    const { showFlag } = useFlags();

    const requestItem = () =>
        axios({
            method: "GET",
            url: config.serverUrl + `v1/users/${username}/`
        });

    const requestSchema = () =>
        axios({
            method: "GET",
            url: config.serverUrl + "v1/schemas/edit-users/"
        });

    return {
        requestItem,
        requestSchema
    };
};
