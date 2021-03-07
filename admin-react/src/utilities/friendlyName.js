export default (firstName, lastName, otherNames) => {
    const otherNameInitials =
        otherNames &&
        otherNames.split(/\s*/).map(name => name.charAt(0).toUppercase() + ".");

    return [firstName, otherNameInitials, lastName].filter(x => !!x).join(" ");
};
