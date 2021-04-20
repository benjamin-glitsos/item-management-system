export default error => {
    const responseErrors = error?.response?.data?.errors;
    if (responseErrors) {
        for (const error of responseErrors) {
            console.error(error);
            console.log("Flag: Error");
        }
    } else {
        console.error(error);
        console.log("Flag: Something went wrong");
    }
};
