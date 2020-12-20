import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Version1Routes {
  def apply(entity: String): Route =
    concat(
        pathPrefix("users")(UsersRoutes(entity))
    )
}
