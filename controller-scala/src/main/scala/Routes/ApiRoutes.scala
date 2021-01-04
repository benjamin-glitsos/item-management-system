import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object ApiRoutes {
  final def apply(): Route = (AccessControl() & HandleRejections())(
    concat(
      pathPrefix("v1")(Version1Routes())
    )
  )
}
