import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._
import akka.http.scaladsl.model._

object ApiRoutes {
  def apply(): Route =
    concat(
        pathPrefix("v1")(Version1Routes())
    )
}
