import doobie.implicits._
import doobie_bundle.connection._
import scala.util.{Try}

trait UsersServicesEdit {
  def edit(oldUsername: String, entityJson: String): String = {
    val body: ujson.Value            = ujson.read(entityJson)
    val newUsername: Option[String]  = Try(body("username").str).toOption
    val password: Option[String]     = Try(body("password").str).toOption
    val emailAddress: Option[String] = Try(body("email_address").str).toOption
    val notes: Option[String]        = Try(body("notes").str).toOption

    UsersDAO
      .edit(oldUsername, newUsername, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
