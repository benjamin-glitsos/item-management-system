import doobie.implicits._
import doobie_bundle.connection._
import scala.util.{Try}
import upickle.default._

trait UsersServicesCreate {
  def create(entityJson: String): String = {
    val body: ujson.Value    = read[ujson.Value](entityJson)
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
