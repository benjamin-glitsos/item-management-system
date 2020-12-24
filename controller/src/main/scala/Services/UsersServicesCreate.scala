import doobie.implicits._
import doobie_bundle.connection._
import scala.util.{Try}

trait UsersServicesCreate {
  def create(entityJson: String) = {
    val body: ujson.Value    = ujson.read(entityJson)
    val username: String     = body("username").str
    val password: String     = body("password").str
    val emailAddress: String = body("email_address").str
    val notes: String        = Try(body("notes").str).getOrElse("")

    UsersDAO
      .create(username, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
