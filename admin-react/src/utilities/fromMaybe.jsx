export default ([x]) => {
    if ([null, undefined, ""].some(x)) {
        return null;
    } else {
        return x;
    }
}
