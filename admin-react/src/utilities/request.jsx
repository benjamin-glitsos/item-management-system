import axios from "axios";

export default async ({ axios: axiosRequest, onSuccess = () => {} }) => {
    console.log(axiosRequest);
    try {
        const response = await axios(axiosRequest);
        onSuccess(response);
    } catch (error) {
        const responseErrors = errors?.response?.data?.errors;
        if (responseErrors) {
            for (const error of list) {
                console.error(error);
                console.log("Flag: Error");
            }
        } else {
            console.error(errors);
            console.log("Flag: Something went wrong");
        }
    }
};
