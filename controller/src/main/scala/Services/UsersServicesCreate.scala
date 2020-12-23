import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import upickle_bundle.implicits._

trait UsersServicesCreate {
  def apply(entityJson: String) = {
    val body: ujson.Value = ujson.read(entityJson)
    val username          = body("username").str
    val password          = body("password").str
    val emailAddress      = body("email_address").str
    val notes             = body("notes").str

    UsersDAO
      .create(username, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
