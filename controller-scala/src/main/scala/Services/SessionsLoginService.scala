import java.sql.SQLException
import doobie.implicits._

trait SessionsLoginService extends ServiceTrait with DoobieConnectionTrait {
  final def login(username: String, password: String): String = {
    try {
      val isAuthenticated: Boolean = UsersDAO
        .authenticate(username, password)
        .transact(transactor)
        .unsafeRunSync
      new String
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
