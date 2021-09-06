import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object Version1Routes {
  final def apply(): Route =
    (LoggingMiddleware() & RejectionHandlerMiddleware() & SessionMiddleware()) {
      concat(
        pathPrefix("schemas")(SchemasRoutes()),
        pathPrefix("sessions")(SessionsRoutes()),
        pathPrefix("users")(UsersRoutes()),
        pathPrefix("items")(ItemsRoutes()),
        PreflightRoutes()
      )
    }
}
