import R from "ramda";

export default (prop, x) => x.every(R.prop(prop));
