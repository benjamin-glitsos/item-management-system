import fromMaybe from "./fromMaybe";

export default (firstName, lastName, otherNames) => {
    const optionalOtherNames = fromMaybe(otherNames);

    const otherNameInitials = optionalOtherNames
        .split(/\s+/)
        .map(name => name[0].toUpperCase() + ".");

    return [firstName, ...otherNameInitials, lastName]
        .filter(x => !!x)
        .join(" ");
};
