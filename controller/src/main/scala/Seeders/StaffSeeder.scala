import scala.util.Random

object StaffSeeder extends Seeder {
    def create() = {
        val person = newPerson()
        val address = person.getAddress()
        val text = newText()

        val randomStaff = Staff(
            id = 0,
            record_id = 0,
            person_id = 0,
            staff_number = randomDigits(12).toString,
            employment_start = currentDate(),
            employment_end = None
        )

        val randomPerson = Person(
            id = 0,
            first_name = person.getFirstName(),
            last_name = person.getLastName(),
            other_names = sometimesNone(0.2, person.getMiddleName()),
            sex_id = if (person.isMale()) 1 else 2,
            date_of_birth = currentDate(),
            email_address = person.getEmail(),
            phone_number = person.getTelephoneNumber(),
            address_line_one = address.getAddressLine1(),
            address_line_two = address.getCity(),
            postcode = address.getPostalCode(),
            is_aboriginal_or_torres_strait_islander = biasedFlip(0.1),
            is_australian_citizen = biasedFlip(0.9),
            is_born_overseas = biasedFlip(0.2),
            is_english_second_language = biasedFlip(0.2)
        )
        // TODO: make these weighted booleans conditional on each other

        // TODO: make staff services take an array of department ids to create?

        StaffServices.create(
            s = randomStaff,
            p = randomPerson,
            user_id = 1,
            notes = randomNotes()
        )
    }
}
