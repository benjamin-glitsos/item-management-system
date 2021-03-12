import fromMaybe from "%/utilities/fromMaybe";

export default (firstName, lastName, otherNames) => {
    const optionalOtherNames = fromMaybe(otherNames);

    const namesList = (() => {
        if (!!optionalOtherNames) {
            const otherNameInitials = optionalOtherNames
                .split(/\s+/)
                .map(name => name.charAt(0).toUpperCase() + ".");

            return [firstName, ...otherNameInitials, lastName];
        } else {
            return [firstName, lastName];
        }
    })();

    return namesList.join(" ");
};
