import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait UsersCreateService {
  final def create(
      username: String,
      emailAddress: String,
      firstName: String,
      lastName: String,
      otherNames: Option[String],
      password: String,
      additionalNotes: Option[String]
  ): String = {
    try {
      UsersDAO
        .create(
          username,
          emailAddress,
          firstName,
          lastName,
          otherNames,
          password,
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
