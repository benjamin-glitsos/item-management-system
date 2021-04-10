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
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
