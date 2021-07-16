import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object SessionMiddleware extends StringMixin {
  final def apply(): Directive0 =
    extractRequest flatMap { request =>
      {
        var method: String = request.method.name
        var uri: String    = request.uri.toString

        var isLoginMethod: Boolean = method == "POST"
        var isLoginRoute: Boolean =
          uri.matches(
            s"^http://localhost:${System.getenv("CONTROLLER_PORT")}/api/v[\\d]+/sessions/$"
          )

        if (isLoginMethod && isLoginRoute) {
          pass
        } else {
          var authenticationToken =
            request.getHeader("X-Auth-Token").toString
          // val authenticationValue: String = SessionsDAO.get(authenticationToken)

          println(method)
          println(uri)
          println(authenticationToken)
          // println(authenticationValue)
          println("=============================")

          pass

          // if (isEmpty(authenticationValue)) {
          //   pass
          // } else {
          //   reject(ValidationRejection("test"))
          // }
        }
      }
    }
}
