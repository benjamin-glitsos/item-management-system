import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object ApiRoutes {
  final def apply(): Route =
    concat(
      pathPrefix("v1")(Version1Routes())
    )
}
