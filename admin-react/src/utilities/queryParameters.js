import config from "%/config";

const isQueryDefault = (value, defaultTest) =>
    defaultTest ? undefined : value;

export const queryPageNumber = pageNumber =>
    isQueryDefault(pageNumber, pageNumber === 1);

export const queryPageLength = pageLength =>
    isQueryDefault(pageLength, pageLength === config.defaultPageLength);

export const querySearch = search => isQueryDefault(search, search === "");
