object UsersSeeder extends SeederUtilities {
    private def randomPassword(): String = {
        randomString(randomBetween(8 to 14))
    }

    def create(staff_id: Int) = {
        val person = newPerson()
        val text = newText()

        UsersServices.create(
            u = User(
                id = 0,
                record_id = 0,
                staff_id,
                username = person.getUsername(),
                password = randomPassword()
            ),
            user_id = 1,
            notes = randomNotes()
        )
    }
}
