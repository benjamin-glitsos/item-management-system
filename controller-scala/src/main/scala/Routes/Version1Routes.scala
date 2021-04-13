import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object Version1Routes {
  final def apply(): Route =
    (AccessControlDirective() & HandleRejectionsDirective())(
      concat(
        pathPrefix("users")(UsersRoutes()),
        pathPrefix("items")(ItemsRoutes()),
        PreflightRoutes()
      )
    )
}
