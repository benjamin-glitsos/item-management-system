object UsersData extends Seeder {
    val seedCount: Int = 4

    val admin: User = User(
        id = id,
        person_id = 1,
        username = System.getenv("ADMIN_USERNAME"),
        password = System.getenv("ADMIN_PASSWORD")
    )

    val data: User = User(
        id = id,
        person_id = randFK(PeopleData.seedCount),
        username = newPerson().getUsername(),
        password = newPerson().getPassword()
    )

    def seed() = {
        seeder[User](seedCount, data)
    }
}
