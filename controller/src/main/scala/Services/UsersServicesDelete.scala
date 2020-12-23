import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._
import upickle_bundle.implicits._

trait UsersServicesDelete {
  def delete(entityJson: String) = {
    val body: ujson.Value = ujson.read(entityJson)
    val method            = body("method").str
    val usernames         = read[List[String]](body("usernames"))

    method match {
      case "soft" =>
        UsersDAO
          .softDelete(usernames)
          .transact(xa)
          .unsafeRunSync
      case "restore" =>
        UsersDAO
          .restoreDelete(usernames)
          .transact(xa)
          .unsafeRunSync
      case "hard" =>
        UsersDAO
          .hardDelete(usernames)
          .transact(xa)
          .unsafeRunSync
    }

    new String
  }
}
