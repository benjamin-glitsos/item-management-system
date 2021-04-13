import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait ItemsDeleteService {
  final def delete(method: String, keys: List[String] = List()): String = {
    try {
      method match {
        case "soft" =>
          ItemsDAO
            .softDelete(keys)
            .transact(xa)
            .unsafeRunSync
        case "restore" =>
          ItemsDAO
            .restoreDelete(keys)
            .transact(xa)
            .unsafeRunSync
        case "hard" =>
          ItemsDAO
            .hardDelete(keys)
            .transact(xa)
            .unsafeRunSync
        case "hard-delete-all-rows" =>
          ItemsDAO
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
