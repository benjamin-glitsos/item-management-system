import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait UsersCreateService extends ServiceTrait {
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
        .transact(transactor)
        .unsafeRunSync
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
