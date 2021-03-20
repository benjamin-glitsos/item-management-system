import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes.NoContent

object PreflightRoutes {
  final def apply(): Route = options {
    complete(NoContent, new String)
  }
}
