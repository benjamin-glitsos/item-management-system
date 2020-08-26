import scala.util.Random

object UsersSeeder extends Seeder {
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
            notes = Some(
                text.sentence(Random.between(1, 3))
            )
        )
    }

    def populateAllStaffIds() = {
        UsersDAO.populateAllStaffIds()
    }

    // TODO: make a service that updates all staff_ids to equal record_ids. Run this after making all the users.
}
