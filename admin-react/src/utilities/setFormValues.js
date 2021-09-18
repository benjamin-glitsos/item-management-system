import nullToEmptyStr from "%/utilities/nullToEmptyStr";

export default (setValue, data) => {
    for (const [key, value] of Object.entries(data || {})) {
        setValue(key, nullToEmptyStr(value));
    }
};
