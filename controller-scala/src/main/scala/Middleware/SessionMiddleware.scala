import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import scala.jdk.OptionConverters._

object SessionMiddleware extends SessionMixin with StringMixin {
  final def apply(): Directive0 =
    extractRequest flatMap { request =>
      {
        var method: String = request.method.name
        var uri: String    = request.uri.toString

        var isLoginMethod: Boolean = method == "POST"
        var isLoginRoute: Boolean =
          uri.matches(s"^${Server.apiUri}/api/v[\\d]+/sessions/$$")
        var isLogin: Boolean = isLoginMethod && isLoginRoute

        if (isLogin) {
          pass // TODO: reject
        } else {
          var maybeAuthenticationToken: Option[String] =
            request.getHeader("X-Auth-Token").toScala.map(_.value)

          maybeAuthenticationToken match {
            case None => pass // TODO: reject
            case Some(authenticationToken) => {
              val (metakey: String, sessionToken: String) =
                decomposeAuthenticationToken(authenticationToken)
              val sessionData: String = SessionsDAO.get(sessionToken)
              pass
            }
          }
        }
      }
    }
}
