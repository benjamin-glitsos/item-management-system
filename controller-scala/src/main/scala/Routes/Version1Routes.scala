import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object Version1Routes {
  final def apply(): Route = (AccessControl() & HandleRejections())(
    concat(
      pathPrefix("users")(UsersRoutes()),
      PreflightRoutes()
    )
  )
}
