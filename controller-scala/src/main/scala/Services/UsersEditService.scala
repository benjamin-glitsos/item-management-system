import doobie.implicits._
import doobie_import.connection._

trait UsersEditService {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      emailAddress: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[String],
      password: Option[String],
      additionalNotes: Option[String]
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
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
