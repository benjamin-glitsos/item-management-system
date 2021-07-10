import java.sql.SQLException
import doobie.implicits._

trait SessionsLoginService
    extends ServiceTrait
    with DoobieConnectionTrait
    with RedisTrait {
  final def login(username: String, password: String): String = {
    try {
      val isAuthenticated: Boolean = UsersDAO
        .authenticate(username, password)
        .transact(transactor)
        .unsafeRunSync
      redis.set("foo", "bar")
      redis.get("foo")
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
