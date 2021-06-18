import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait UsersDeleteService extends ServiceTrait {
  final def delete(method: String, usernames: List[String] = List()): String = {
    try {
      method match {
        case "soft" =>
          UsersDAO
            .softDelete(usernames)
            .transact(transactor)
            .unsafeRunSync
        case "restore" =>
          UsersDAO
            .restoreDelete(usernames)
            .transact(transactor)
            .unsafeRunSync
        case "hard" =>
          UsersDAO
            .hardDelete(usernames)
            .transact(transactor)
            .unsafeRunSync
        case "hard-delete-all-rows" =>
          UsersDAO
            .hardDeleteAllRows()
            .transact(transactor)
            .unsafeRunSync
      }
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
