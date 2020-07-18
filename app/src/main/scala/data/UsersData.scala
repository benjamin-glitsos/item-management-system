object UsersData extends Seeder {
    val seedCount: Int = 4

    val admin: User = User(
        id = id,
        person_id = 1,
        username = System.getenv("ADMIN_USERNAME"),
        password = System.getenv("ADMIN_PASSWORD")
    )

    def data() = {
        seeder[User](seedCount, User(
            id = id,
            person_id = randFK(PeopleData.seedCount),
            username = newPerson().getUsername(),
            password = newPerson().getPassword()
        ))
    }
}
