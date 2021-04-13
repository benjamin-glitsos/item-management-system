import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait UsersDeleteService {
  final def delete(method: String, usernames: List[String] = List()): String = {
    try {
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
        case "hard-delete-all-rows" =>
          UsersDAO
            .hardDeleteAllRows()
            .transact(xa)
            .unsafeRunSync
      }
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
