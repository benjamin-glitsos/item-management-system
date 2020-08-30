object StaffSeeder extends SeederUtilities with LogicUtilities {
    private def randomLicenseNumber(): String = {
        val length = 12
        randomFixedDigits(length).toString
    }

    private def randomPostcode(): String = {
        2.toString + randomFixedDigits(3).toString
    }

    def create() = {
        val person = newPerson()
        val address = person.getAddress()
        val text = newText()

        val is_australian_citizen = biasedFlip(9/10)
        val is_aboriginal_or_torres_strait_islander = if (is_australian_citizen) biasedFlip(1/20) else false
        val is_born_overseas = if (is_australian_citizen) biasedFlip(1/5) else true
        val is_english_second_language = if (any(List(
            ! is_australian_citizen,
            is_aboriginal_or_torres_strait_islander,
            is_born_overseas
        ))) biasedFlip(1/4) else biasedFlip(1/10)

        val thisStaff = Staff(
            id = 0,
            record_id = 0,
            person_id = 0,
            staff_number = randomLicenseNumber(),
            employment_start = currentDate(),
            employment_end = None // TODO: use today minus random number of days. Also use randomExists
        )

        val thisPerson = Person(
            id = 0,
            first_name = person.getFirstName(),
            last_name = person.getLastName(),
            other_names = normaliseEmptyString(person.getMiddleName()),
            sex_id = if (person.isMale()) 1 else 2,
            date_of_birth = currentDate(),
            email_address = person.getEmail(),
            phone_number = person.getTelephoneNumber(),
            address_line_one = address.getAddressLine1(),
            address_line_two = randomExists(2/3, address.getCity()),
            postcode = randomPostcode(),
            is_aboriginal_or_torres_strait_islander,
            is_australian_citizen,
            is_born_overseas,
            is_english_second_language
        )

        val thisDepartments = () => {
            // TODO: make a Count function for the departments table then use a monad to feed this into the departments service so you can do this
            val length = randomBetween(1 to 4)
            val maxId = DepartmentsSeeder.data.length()
            List.fill(length)(randomNextInt(maxId))
        }

        StaffServices.create(
            s = thisStaff,
            p = thisPerson,
            d_ids = thisDepartments,
            user_id = 1,
            notes = randomNotes()
        )
    }
}
