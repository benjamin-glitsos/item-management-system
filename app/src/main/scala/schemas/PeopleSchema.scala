import slick.driver.PostgresDriver.api._
import Types._

class PeopleSchema(tag: Tag) extends Table[Person](tag, "people") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def record_id = column[Int]("record_id")
    def first_name = column[String]("first_name")
    def last_name = column[String]("last_name")
    def other_names = column[Option[String]]("other_names")
    def sex_id = column[Int]("sex_id")
    def email_address = column[String]("email_address")
    def phone_number = column[String]("phone_number")
    def address_line_one = column[String]("address_line_one")
    def address_line_two = column[String]("address_line_two")
    def zip = column[String]("zip")
    def * = (
        id,
        record_id,
        first_name,
        last_name,
        other_names,
        sex_id,
        email_address,
        phone_number,
        address_line_one,
        address_line_two,
        zip
    ) <> (Person.tupled, Person.unapply)
    def records_fk = foreignKey("records_fk", record_id, RecordsDAO)(_.id)
    def sex_fk = foreignKey("sex_fk", sex_id, SexDAO)(_.id)
}
