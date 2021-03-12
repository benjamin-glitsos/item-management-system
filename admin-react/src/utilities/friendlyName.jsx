import toMaybe from "./toMaybe";

export default (firstName, lastName, otherNames) => {
    const firstNameMaybe = toMaybe(firstName);
    const lastNameMaybe = toMaybe(lastName);
    const otherNamesMaybe = toMaybe(otherNames).map(
        s => s.split(/\s+/).map(name => name.charAt(0).toUpperCase() + ".").join(" ")
    );

    return [firstNameMaybe, otherNamesMaybe, lastNameMaybe].filter(x => x.emit()).join(" ")
};
