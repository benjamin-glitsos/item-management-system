import R from "ramda";
import noNewDataToSubmitToast from "utilities/noNewDataToSubmitToast";
import successToast from "utilities/successToast";
import simplur from "simplur";

export default (refreshForm, data) => {
    if (R.isEmpty(data)) {
        noNewDataToSubmitToast();
    } else {
        const numberOfChanges = Object.keys(data).length;
        console.log(data);
        refreshForm();
        successToast({
            title: simplur`Changed ${numberOfChanges} field[|s]`,
            description: "Changes were submitted."
        });
    }
};
