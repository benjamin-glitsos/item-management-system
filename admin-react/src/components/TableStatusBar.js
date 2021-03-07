export default ({ placeholder, numberOfSelected }) => {
    if (numberOfSelected === 0) {
        return placeholder;
    } else {
        return `${numberOfSelected} selected.`;
    }
};
