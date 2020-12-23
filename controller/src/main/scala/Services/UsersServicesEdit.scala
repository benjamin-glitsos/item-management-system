import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import upickle_bundle.implicits._
import scala.util.{Try}

trait UsersServicesEdit {
  def edit(username: String, entityJson: String) = {
    val body: ujson.Value = ujson.read(entityJson)
    val username          = Try(body("username").str)
    val password          = Try(body("password").str)
    val emailAddress      = Try(body("email_address").str)
    val notes             = Try(body("notes").str)

    new String
  }
}
