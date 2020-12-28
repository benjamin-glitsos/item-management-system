import doobie.implicits._
import doobie_bundle.connection._

trait UsersServicesDelete {
  final def delete(method: String, usernames: List[String]): String = {
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
  }
}
