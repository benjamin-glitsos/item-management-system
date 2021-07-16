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
            val sessionToken: String = randomSessionToken()

            val sessionKey: String = sessionNamespace(metakey)

            // SessionsDAO.get(sessionKey)
            //
            // SessionsDAO.set(sessionKey, "1")

            sessionKey

            // if (!isEmpty(existingSessionValue)) {
            //   "TODO" // TODO
            // } else {
            //   val sessionValue: String = write(
            //     ujson.Obj(
            //       "token"     -> ujson.Str(sessionToken),
            //       "timestamp" -> ujson.Num(epochNow)
            //     )
            //   )
            //
            //   val authenticationToken: String = makeAuthenticationToken(
            //     metakey,
            //     sessionToken
            //   )
            //
            //   SessionsDAO.set(sessionKey, sessionValue)
            //
            //   write(
            //     ujson.Obj(
            //       "data" -> ujson.Obj(
            //         "authentication_token" -> ujson.Str(authenticationToken)
            //       )
            //     )
            //   )
            // }
          }
        }
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
