import slick.driver.PostgresDriver.api._
import SchemaTypes._

class PeopleSchema(tag: Tag) extends Table[Person](tag, "people") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def record_id = column[Int]("record_id")
    def first_name = column[String]("first_name")
    def last_name = column[String]("last_name")
    def other_names = column[Option[String]]("other_names")
    def email_address = column[String]("email_address")
    def phone_number = column[String]("phone_number")
    def address_line_one = column[String]("address_line_one")
    def address_line_two = column[Option[String]]("address_line_two")
    def state_territory = column[String]("state_territory")
    def postcode = column[String]("postcode")
    def * = (
        id,
        record_id,
        first_name,
        last_name,
        other_names,
        email_address,
        phone_number,
        address_line_one,
        address_line_two,
        state_territory,
        postcode
    )
    def records_fk = foreignKey("records_fk", record_id, RecordsDAO)(_.id)
}
