import doobie.implicits._
import doobie_bundle.connection._

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
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}
