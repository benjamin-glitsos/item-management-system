import java.util.Date

case class Person(
    id: Int,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    sex_id: Int,
    date_of_birth: Date,
    email_address: String,
    phone_number: String,
    address_line_one: String,
    address_line_two: Option[String],
    postcode: String,
    is_aboriginal_or_torres_strait_islander: Boolean,
    is_australian_citizen: Boolean,
    is_born_overseas: Boolean,
    is_english_second_language: Boolean
)
