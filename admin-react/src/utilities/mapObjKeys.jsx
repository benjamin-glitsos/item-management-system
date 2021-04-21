import R from "ramda";

const mapObjKeys = f =>
    R.pipe(
        x => Object.entries,
        R.map(([key, value]) => [key, f(value)]),
        x => Object.fromEntries
    );

export default mapObjKeys;
