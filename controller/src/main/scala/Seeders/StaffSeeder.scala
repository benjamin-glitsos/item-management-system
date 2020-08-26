import scala.util.Random

object StaffSeeder extends Seeder {
    def create() = {
        val person = newPerson()
        val text = newText()

        StaffServices.create(
            s = Staff(),
            p = Person(),
            user_id = 1,
            notes = randomNotes()
        )
    }
}
