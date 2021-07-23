import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes {
  final def apply(): Route =
    (ExceptionHandlerMiddleware() & CorsMiddleware() & RedirectTrailingSlashMiddleware()) {
      concat(
        pathPrefix("api")(ApiRoutes())
      )
    }
}
