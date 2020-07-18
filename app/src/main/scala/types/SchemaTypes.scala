import java.sql.Timestamp

object SchemaTypes {
    case class Record(
        id: Int,
        created_at: Option[Timestamp],
        created_by: Option[Int],
        updated_at: Option[Timestamp],
        updated_by: Option[Int],
        deleted_at: Option[Timestamp],
        deleted_by: Option[Int]
    )

    case class Person(
        id: Int,
        record_id: Int,
        first_name: String,
        last_name: String,
        other_names: Option[String],
        sex_id: Int,
        email_address: String,
        phone_number: String,
        address_line_one: String,
        address_line_two: String,
        zip: String
    )

    case class User(
        id: Int,
        person_id: Int,
        username: String,
        password: String
    )

    case class Sex(id: Int, name: String)
}
