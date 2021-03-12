import Maybe from "simple-maybe";

export default x => {
    if (x === []) {
        return Maybe.of(1);
    } else {
        return x;
    }
};
