import { encodeDelimitedArray, decodeDelimitedArray } from "use-query-params";

export const CommaArrayParam = {
    encode: ls => encodeDelimitedArray(ls, ","),
    decode: s => decodeDelimitedArray(s, ",")
};
