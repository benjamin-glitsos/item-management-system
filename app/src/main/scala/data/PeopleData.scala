object PeopleData extends Seeder {
    val seedCount: Int = 6

    val admin: Person = Person(
        id = id,
        record_id = 1,
        first_name = System.getenv("SUPER_FIRST_NAME"),
        last_name = System.getenv("SUPER_LAST_NAME"),
        other_names = Some(System.getenv("SUPER_MIDDLE_NAME")),
        sex_id = System.getenv("SUPER_SEX").toInt,
        email_address = System.getenv("SUPER_EMAIL"),
        phone_number = System.getenv("SUPER_PHONE"),
        address_line_one = System.getenv("SUPER_ADDRESS_LINE_1"),
        address_line_two = System.getenv("SUPER_ADDRESS_LINE_2"),
        zip = System.getenv("SUPER_ZIP")
    )

    def data() = {
        seeder[Person](
            seedCount,
            Person(
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
        )
    }
}
