import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait SessionsLoginService
    extends ServiceMixin
    with DoobieConnectionMixin
    with SessionMixin
    with EpochMixin {
  final def login(username: String, password: String): ujson.Value = {
    read[ujson.Value](
      try {
        val metakeyIfAuthenticated: Option[String] = UsersDAO
          .authenticate(username, password)
          .transact(transactor)
          .unsafeRunSync

        metakeyIfAuthenticated match {
          case None => {
            new String
          }
          case Some(metakey) => {
            val authenticationToken: String = makeAuthenticationToken(
              metakey,
              randomSessionToken()
            )

            val sessionKey: String = sessionNamespace(authenticationToken)

            val sessionValue: String = write(
              ujson.Obj(
                "timestamp" -> ujson.Num(epochNow)
              )
            )

            val output: String = write(
              ujson.Obj(
                "data" -> ujson.Obj(
                  "authentication_token" -> ujson.Str(authenticationToken)
                )
              )
            )

            redis.set(sessionKey, sessionValue)

            output
          }
        }
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
