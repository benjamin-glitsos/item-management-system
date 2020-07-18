object PeopleData extends Seeder {
    val seedCount: Int = 6

    val admin: Person = Person(
        id = id,
        record_id = 1,
        first_name = System.getenv("ADMIN_FIRST_NAME"),
        last_name = System.getenv("ADMIN_LAST_NAME"),
        other_names = Some(System.getenv("ADMIN_MIDDLE_NAME")),
        sex_id = System.getenv("ADMIN_SEX").toInt,
        email_address = System.getenv("ADMIN_EMAIL"),
        phone_number = System.getenv("ADMIN_PHONE"),
        address_line_one = System.getenv("ADMIN_ADDRESS_LINE_1"),
        address_line_two = System.getenv("ADMIN_ADDRESS_LINE_2"),
        zip = System.getenv("ADMIN_ZIP")
    )

    val data: Person = Person(
        id = id,
        record_id = randFK(RecordsData.seedCount),
        first_name = newPerson().getFirstName(),
        last_name = newPerson().getLastName(),
        other_names = Some(newPerson().getMiddleName()),
        sex_id = randFK(SexData.seedCount), // TODO: not randomising?
        email_address = newPerson().getEmail(),
        phone_number = newPerson().getTelephoneNumber(),
        address_line_one = newPerson().getAddress().getAddressLine1(),
        address_line_two = newPerson().getAddress().getCity(),
        zip = newPerson().getAddress().getPostalCode()
    )

    def seed() = {
        seeder[Person](seedCount, data)
    }
}
