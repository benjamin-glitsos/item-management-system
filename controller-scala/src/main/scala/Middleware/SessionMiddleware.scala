import java.util.UUID
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import scala.jdk.OptionConverters._
import upickle.default._

object SessionMiddleware extends SessionMixin with StringMixin with EpochMixin {
  final def apply(): Directive0 =
    extractRequest flatMap { request =>
      {
        val method: String = request.method.name
        val uri: String    = request.uri.toString

        val isLoginMethod: Boolean = method == "POST"
        val isLoginRoute: Boolean =
          uri.matches(s"^${Server.apiUri}/api/v[\\d]+/sessions/$$")
        val isLogin: Boolean = isLoginMethod && isLoginRoute

        if (isLogin) {
          pass // TODO: reject
        } else {
          val maybeAuthenticationToken: Option[String] =
            request.getHeader("X-Auth-Token").toScala.map(_.value)

          maybeAuthenticationToken match {
            case None => pass // TODO: reject
            case Some(authenticationToken) => {
              if (
                authenticationToken == System.getenv(
                  "SESSION_SUPER_AUTHENTICATION_TOKEN"
                )
              ) {
                pass
              } else {
                var (metakey: String, sessionToken: UUID) =
                  splitAuthenticationToken(authenticationToken)
                val maybeSessionData: Option[String] =
                  Option(SessionsDAO.get(sessionNamespace(metakey)))

                maybeSessionData match {
                  case None => pass // TODO: reject
                  case Some(sessionData) => {
                    val j: ujson.Value = read[ujson.Value](sessionData)
                    val timestamp: Int = j("timestamp").num.toInt
                    val token: String  = j("token").str

                    val isYoungerThanThirtyMinutes: Boolean =
                      (epochNow() - timestamp) < minutes(30)

                    if (!isYoungerThanThirtyMinutes) {
                      pass // TODO: reject
                    } else if (token != sessionToken) {
                      pass // TODO: reject
                    } else {
                      pass
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
}
