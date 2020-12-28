import doobie.implicits._
import doobie_bundle.connection._
import upickle.default._

trait UsersServicesCreate {
  def create(
      username: String,
      password: String,
      emailAddress: String,
      notes: String
  ): String = {
    UsersDAO
      .create(username, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
