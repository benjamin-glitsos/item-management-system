import java.util.UUID
import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait SessionsLoginService
    extends ServiceMixin
    with DoobieConnectionMixin
    with EpochMixin
    with StringMixin
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
            new String
          }
          case Some(metakey) => {
            def makeOutput(authenticationToken: String): String = {
              write(
                ujson.Obj(
                  "data" -> ujson.Obj(
                    "authentication_token" -> ujson.Str(authenticationToken)
                  )
                )
              )
            }

            val sessionKey: String = sessionNamespace(metakey)

            val maybeSessionValue: Option[String] =
              Option(SessionsDAO.get(sessionKey))

            maybeSessionValue match {
              case Some(sessionValue) => {
                val value: ujson.Value = read[ujson.Value](sessionValue)
                val sessionToken: UUID = UUID.fromString(value("token").str)

                makeOutput(makeAuthenticationToken(metakey, sessionToken))
              }
              case None => {
                val sessionToken: UUID = randomSessionToken()

                val sessionValue: String = write(
                  ujson.Obj(
                    "timestamp" -> ujson.Num(epochNow),
                    "token"     -> ujson.Str(sessionToken.toString)
                  )
                )

                val authenticationToken: String = s"$metakey.$sessionToken"

                SessionsDAO.set(sessionKey, sessionValue)

                makeOutput(authenticationToken)
              }
            }
          }
        }
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
