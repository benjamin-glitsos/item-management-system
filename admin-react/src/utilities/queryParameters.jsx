import config from "%/config";

const isQueryDefault = (defaultTest, value) =>
    defaultTest ? undefined : value;

export const queryPageNumber = pageNumber =>
    isQueryDefault(pageNumber === 1, pageNumber);

export const queryPageLength = pageLength =>
    isQueryDefault(pageLength === config.defaultPageLength, pageLength);

export const querySearch = search => isQueryDefault(search === "", search);

export const querySort = sort => isQueryDefault(sort === [], sort);
