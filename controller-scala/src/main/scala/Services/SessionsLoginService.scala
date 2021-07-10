import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait SessionsLoginService extends ServiceTrait {
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
