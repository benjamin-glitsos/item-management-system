import doobie.implicits._
import doobie_import.connection._

trait ItemsEditService {
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
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
