import R from "ramda";

const mapObjKeys = R.curry((f, x) =>
    R.pipe(
        x => Object.entries,
        R.map(([key, value]) => [key, f(value)]),
        x => Object.fromEntries
    )(x)
);

export default mapObjKeys;
