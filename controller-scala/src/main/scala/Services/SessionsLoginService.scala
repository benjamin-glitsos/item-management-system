import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._

trait SessionsLoginService extends ServiceTrait {
  final def login(username: String, password: String): String = {
    try {
      UsersDAO
        .authenticate(username, password)
        .transact(transactor)
        .unsafeRunSync
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
