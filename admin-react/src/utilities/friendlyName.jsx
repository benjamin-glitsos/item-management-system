import fromMaybe from "./fromMaybe";

export default (firstName, lastName, otherNames) => {
    const optionalOtherNames = fromMaybe(otherNames);

    const otherNameInitials = optionalOtherNames
        .split(/\s+/)
        .map(name => name.charAt(0).toUpperCase() + ".");

    return [firstName, ...otherNameInitials, lastName]
        .filter(x => !!x)
        .join(" ");
};
