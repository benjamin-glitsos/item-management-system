import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes {
  def apply(): Route =
    redirectToTrailingSlashIfMissing(StatusCodes.MovedPermanently) {
      concat(
        pathPrefix("api")(ApiRoutes())
      )
    }
}
