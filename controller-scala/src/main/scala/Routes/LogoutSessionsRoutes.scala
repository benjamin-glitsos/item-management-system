import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object LogoutSessionsRoutes extends UpickleMixin {
  final def apply(): Route = delete {
    (SetActionKeyMiddleware(
      "login-sessions"
    ) & ValidationMiddleware() & headerValueByName("X-Auth-Token")) {
      (body: ujson.Value, authenticationToken: String) =>
        complete(SessionsService.logout(authenticationToken))
    }
  }
}
