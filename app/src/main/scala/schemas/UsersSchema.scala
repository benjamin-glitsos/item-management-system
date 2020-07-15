import slick.driver.PostgresDriver.api._
import java.sql.Timestamp
import SchemaTypes._

class UsersSchema(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def record_id = column[Int]("record_id")
    def username = column[String]("username")
    def password = column[String]("password")
    def * = (id, record_id, username, password)
    def record_fk = foreignKey("record_fk", record_id, RecordsDAO)(_.id)
}
