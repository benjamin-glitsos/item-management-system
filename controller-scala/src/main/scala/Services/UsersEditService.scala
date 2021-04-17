import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait UsersEditService extends ServiceTrait {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[String],
      emailAddress: Option[String],
      password: Option[String],
      additionalNotes: Option[Option[String]]
  ): String = {
    try {
      UsersDAO
        .edit(
          oldUsername,
          newUsername,
          firstName,
          lastName,
          otherNames,
          emailAddress,
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
