import numbersToWords from "number-to-words";
import capitaliseFirstLetter from "%/utilities/capitaliseFirstLetter";

export default ({ placeholder, numberOfSelected }) => {
    if (numberOfSelected === 0) {
        return placeholder;
    } else {
        return `${capitaliseFirstLetter(
            numbersToWords.toWords(numberOfSelected)
        )} selected.`;
    }
};
