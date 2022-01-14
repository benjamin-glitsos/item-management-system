import R from "ramda";

export default (prop, x) => x.some(R.prop(prop));
