import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes {
  final def apply(): Route =
    (ExceptionHandlerMiddleware() & CorsMiddleware()) {
      concat(
        pathPrefix("api")(ApiRoutes())
      )
    }
}
