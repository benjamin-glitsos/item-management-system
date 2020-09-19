object UsersSeeder extends SeederUtilities {
    private def randomPassword(): String = {
        randomString(randomBetween(8 to 14))
    }

    // TODO: this seeder should create staff then return Id and create user. Remove StaffSeeder and merge it into this
    def create() = {
        val person = newPerson()
        val text = newText()

        UsersServices.create(
            user = User(
                id = 0,
                record_id = 0,
                staff_id = 1,
                username = person.getUsername(),
                password = randomPassword()
            ),
            user_username = System.getenv("SUPER_USERNAME"),
            notes = randomNotes()
        )
    }

    def populateAllStaffIds() = {
        UsersDAO.populateAllStaffIds()
    }
}
