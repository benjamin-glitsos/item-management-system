import numbersToWords from "number-to-words";
import capitalise from "../utilities/capitalise";

export default ({ placeholder, numberOfSelected }) => {
    if (numberOfSelected === 0) {
        return placeholder;
    } else {
        return `${capitalise(
            numbersToWords.toWords(numberOfSelected)
        )} selected.`;
    }
};
