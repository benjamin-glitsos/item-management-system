import java.sql.SQLException
import doobie.implicits._

trait ItemsDeleteService extends ServiceMixin with DoobieConnectionMixin {
  final def delete(method: String, skus: List[String] = List()): String = {
    try {
      method match {
        case "soft" =>
          ItemsDAO
            .softDelete(skus)
            .transact(transactor)
            .unsafeRunSync
        case "restore" =>
          ItemsDAO
            .restoreDelete(skus)
            .transact(transactor)
            .unsafeRunSync
        case "hard" =>
          ItemsDAO
            .hardDelete(skus)
            .transact(transactor)
            .unsafeRunSync
        case "hard-delete-all-rows" =>
          ItemsDAO
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
