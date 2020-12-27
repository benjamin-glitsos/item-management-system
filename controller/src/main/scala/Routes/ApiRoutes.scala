import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object ApiRoutes {
  def apply(): Route = HandleRejections(
    concat(
      pathPrefix("v1")(Version1Routes())
    )
  )
}
