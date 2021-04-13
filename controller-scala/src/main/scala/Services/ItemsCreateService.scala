import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait ItemsCreateService extends ServiceTrait {
  final def create(
      key: String,
      name: String,
      description: Option[String],
      additionalNotes: Option[String]
  ): String = {
    try {
      ItemsDAO
        .create(
          key,
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
