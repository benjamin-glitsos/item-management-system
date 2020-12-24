import doobie.implicits._
import doobie_bundle.connection._
import scala.util.{Try}

trait UsersServicesEdit {
  def edit(oldUsername: String, entityJson: String) = {
    val body: ujson.Value = ujson.read(entityJson)
    val newUsername       = Try(body("username").str).toOption
    val password          = Try(body("password").str).toOption
    val emailAddress      = Try(body("email_address").str).toOption
    val notes             = Try(body("notes").str).toOption

    UsersDAO
      .edit(oldUsername, newUsername, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
