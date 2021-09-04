import R from "ramda";

const emptyStringsToNull = R.map(x => {
    if (typeof x === "string") {
        if (x.trim().length === 0) {
            return null;
        } else {
            return x;
        }
    } else {
        return x;
    }
});

export default emptyStringsToNull;
