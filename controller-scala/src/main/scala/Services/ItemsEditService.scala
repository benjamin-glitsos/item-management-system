import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait ItemsEditService extends ServiceTrait {
  final def edit(
      oldKey: String,
      newKey: Option[String],
      name: Option[String],
      description: Option[String],
      additionalNotes: Option[String]
  ): String = {
    try {
      ItemsDAO
        .edit(
          oldKey,
          newKey,
          name,
          description,
          additionalNotes
        )
        .transact(xa)
        .unsafeRunSync
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
