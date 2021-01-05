import doobie.implicits._
import doobie_bundle.connection._

trait UsersServicesCreate {
  final def create(
      username: String,
      password: String,
      emailAddress: String,
      notes: String
  ): String = {
    try {
      UsersDAO
        .create(username, password, emailAddress, notes)
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
