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
}
