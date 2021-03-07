import doobie.implicits._
import doobie_bundle.connection._

trait UsersServicesCreate {
  final def create(
      username: String,
      emailAddress: String,
      firstName: String,
      lastName: String,
      otherNames: String,
      password: String,
      notes: String
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
          notes
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
