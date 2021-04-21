import R from "ramda";

const mapObjKeys = R.curry((f, x) =>
    R.pipe(
        Object.entries,
        R.map(([key, value]) => [f(key), value]),
        Object.fromEntries
    )(x)
);
export default mapObjKeys;
