export default ([x]) => {
    if ([null, undefined, "", [], [""]].some(y => y === x)) {
        return null;
    } else {
        return x;
    }
}
