import R from "ramda";
import noNewDataToSubmitToast from "utilities/noNewDataToSubmitToast";
import successToast from "utilities/successToast";
import simplur from "simplur";

export default data => {
    if (R.isEmpty(data)) {
        noNewDataToSubmitToast();
    } else {
        const numberOfChanges = Object.keys(data).length;
        console.log(data);
        successToast({
            title: simplur`Changed ${numberOfChanges} field[|s]`,
            description: "Changes were submitted."
        });
    }
};
