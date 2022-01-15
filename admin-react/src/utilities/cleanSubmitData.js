import R from "ramda";
import trimAll from "utilities/trimAll";
import emptyStringsToNull from "utilities/emptyStringsToNull";
import { diff } from "deep-object-diff";
import removeAllUndefined from "utilities/removeAllUndefined";

export default (itemData, data) =>
    R.pipe(
        trimAll,
        emptyStringsToNull,
        x => diff(itemData, x),
        removeAllUndefined
    )(data);
