import R from "ramda";

export default x => R.reject(R.equals(undefined))(x);
