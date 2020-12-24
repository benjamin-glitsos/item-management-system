import upickle.default._
import doobie.implicits._
import doobie_bundle.connection._

trait UsersServicesDelete {
  def delete(entityJson: String) = {
    val body: ujson.Value       = ujson.read(entityJson)
    val method: String          = body("method").str
    val usernames: List[String] = read[List[String]](body("usernames"))

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
