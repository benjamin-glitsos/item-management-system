import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.AuthenticationFailedRejection
import akka.http.scaladsl.model.{HttpMethod, POST}

object SessionMiddleware extends StringMixin {
  final def apply(): Directive0 =
    extractRequest flatMap { request =>
      {
        var method: HttpMethod = request.method
        var uri: String        = request.uri.toString

        var isLoginMethod: Boolean = method.isInstanceOf[POST]
        var isLoginRoute: Boolean =
          uri.startsWith(s"${Server.apiUri}/api/v1/sessions/")

        if (isLoginMethod && isLoginRoute) {
          pass
        } else {
          var authenticationToken: List[String] =
            request.getHeader("X-Auth-Token").headOption.map(_.toString)
          val authenticationValue: String = SessionsDAO.get(authenticationToken)

          println(method)
          println(uri)
          println(authenticationToken)
          println(authenticationValue)
          println("=============================")

          if (isEmpty(authenticationValue)) {
            pass
          } else {
            reject(new AuthorisationFailedRejection)
          }
        }
      }
    }
}
