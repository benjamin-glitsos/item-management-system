import java.sql.Timestamp
import slick.driver.PostgresDriver.api._
import Types._

class RecordsSchema(tag: Tag) extends Table[Record](tag, "records") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def created_at = column[Timestamp]("created_at")
    def created_by = column[Int]("created_by")
    def updated_at = column[Timestamp]("updated_at")
    def updated_by = column[Int]("updated_by")
    def deleted_at = column[Timestamp]("deleted_at")
    def deleted_by = column[Int]("deleted_by")
    def * = (
        id,
        created_at,
        created_by,
        updated_at,
        updated_by,
        deleted_at,
        deleted_by
    )
    def created_by_fk = foreignKey("created_by_fk", created_by, UsersDAO)(_.id)
    def updated_by_fk = foreignKey("updated_by_fk", updated_by, UsersDAO)(_.id)
    def deleted_by_fk = foreignKey("deleted_by_fk", deleted_by, UsersDAO)(_.id)
}
