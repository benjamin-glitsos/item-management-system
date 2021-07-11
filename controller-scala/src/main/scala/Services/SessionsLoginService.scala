import java.sql.SQLException
import doobie.implicits._

trait SessionsLoginService
    extends ServiceMixin
    with DoobieConnectionMixin
    with RedisMixin {
  final def login(username: String, password: String): String = {
    try {
      val metakeyIfAuthenticated: Option[String] = UsersDAO
        .authenticate(username, password)
        .transact(transactor)
        .unsafeRunSync

      metakeyIfAuthenticated match {
        case None => {
          redis.set("foo", "bar")
          redis.get("foo")
        }
        case Some(metakey) => metakey
      }
    } catch {
      case e: SQLException => handleSqlException(e)
    }
  }
}
