import R from "ramda";

const trimAll = R.map(x => {
    if (typeof x === "string") {
        return x.trim();
    } else {
        return x;
    }
});

export default trimAll;
