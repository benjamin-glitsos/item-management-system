import doobie.implicits._
import doobie_import.connection._

trait ItemsCreateService {
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
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
