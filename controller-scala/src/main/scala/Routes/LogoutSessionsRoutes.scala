import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object LogoutSessionsRoutes extends UpickleMixin {
  final def apply(): Route = delete {
    headerValueByName("X-Auth-Token") { authenticationToken =>
      complete(SessionsService.logout(authenticationToken))
    }
  }
}
