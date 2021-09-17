import R from "ramda";

const removeAllUndefined = R.reject(R.equals(undefined));

export default removeAllUndefined;
