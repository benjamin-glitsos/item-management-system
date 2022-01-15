import someHaveProp from "utilities/someHaveProp";
import allHaveProp from "utilities/allHaveProp";
import LoadingSpinnerContent from "modules/LoadingSpinnerContent";
import ErrorMessageContent from "modules/ErrorMessageContent";

export default ({ queries, maxWidth, children }) => {
    const ErrorMessage = <ErrorMessageContent maxWidth={maxWidth} />;
    const LoadingMessage = <LoadingSpinnerContent maxWidth={maxWidth} />;

    if (someHaveProp("isError", queries)) {
        return ErrorMessage;
    } else if (someHaveProp("isLoading", queries)) {
        return LoadingMessage;
    } else if (allHaveProp("isSuccess", queries)) {
        return children;
    } else {
        return ErrorMessage;
    }
};
