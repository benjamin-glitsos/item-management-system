object UsersSeeder extends SeederUtilities {
    private def randomPassword(): String = {
        randomString(randomBetween(8, 14))
    }

    def deleteAll() = {
        UsersDAO.deleteAll()
    }

    def create() = {
        val person = newPerson()
        val text = newText()

        UsersServices.create(
            u = User(
                id = 0,
                record_id = 0,
                staff_id = 1,
                username = person.getUsername(),
                password = randomPassword()
            ),
            user_id = 1,
            notes = randomNotes()
        )
    }

    def populateAllStaffIds() = {
        UsersDAO.populateAllStaffIds()
    }
}
