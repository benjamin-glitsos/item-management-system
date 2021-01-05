import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object ApiRoutes {
  private final def preflightRoute(): Route = options(complete(new String))

  final def apply(): Route = (AccessControl() & HandleRejections())(
    concat(
      pathPrefix("v1")(Version1Routes()),
      preflightRoute()
    )
  )
}
