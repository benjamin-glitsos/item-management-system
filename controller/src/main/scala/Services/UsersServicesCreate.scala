import doobie.implicits._
import doobie_bundle.connection._
import scala.util.{Try}

trait UsersServicesCreate {
  def create(entityJson: String) = {
    val body: ujson.Value = ujson.read(entityJson)
    val username          = body("username").str
    val password          = body("password").str
    val emailAddress      = body("email_address").str
    val notes             = Try(body("notes").str).getOrElse("")

    UsersDAO
      .create(username, password, emailAddress, "")
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
