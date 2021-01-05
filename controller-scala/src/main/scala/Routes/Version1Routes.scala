import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Version1Routes {
  final def apply(): Route =
    concat(
      pathPrefix("users")(UsersRoutes())
    )
}
