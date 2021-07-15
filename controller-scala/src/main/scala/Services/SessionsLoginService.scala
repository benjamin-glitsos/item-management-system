import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait SessionsLoginService
    extends ServiceMixin
    with DoobieConnectionMixin
    with SessionMixin {
  final def login(username: String, password: String): ujson.Value = {
    read[ujson.Value](
      try {
        val metakeyIfAuthenticated: Option[String] = UsersDAO
          .authenticate(username, password)
          .transact(transactor)
          .unsafeRunSync

        metakeyIfAuthenticated match {
          case None => {
            // redis.set("foo", "bar")
            // redis.get("foo")
            new String
          }
          case Some(metakey) => {
            val sessionToken = randomSessionToken()
            val authenticationToken = makeAuthenticationToken(
              metakey,
              sessionToken
            )
            write(
              ujson.Obj(
                "data" -> ujson.Obj(
                  "authentication_token" -> ujson.Str(authenticationToken)
                )
              )
            )
          }
        }
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
