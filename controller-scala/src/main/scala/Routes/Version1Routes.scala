import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object Version1Routes {
  protected final val middlewares: Directive0 = (
    ExceptionHandleMiddleware()
      & RejectionHandleMiddleware()
      & CorsMiddleware()
      & SessionMiddleware()
  )

  final def apply(): Route = middlewares(
    concat(
      pathPrefix("schemas")(SchemasRoutes()),
      pathPrefix("sessions")(SessionsRoutes()),
      pathPrefix("users")(UsersRoutes()),
      pathPrefix("items")(ItemsRoutes()),
      PreflightRoutes()
    )
  )
}
