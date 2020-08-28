object UsersSeeder extends SeederUtilities {
    def create() = {
        val person = newPerson()
        val text = newText()

        UsersServices.create(
            u = User(
                id = 0,
                record_id = 0,
                staff_id = 1,
                username = person.getUsername(),
                password = person.getPassword()
            ),
            user_id = 1,
            notes = randomNotes()
        )
    }

    def populateAllStaffIds() = {
        UsersDAO.populateAllStaffIds()
    }
}
