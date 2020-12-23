import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.HttpEntity.Chunked

object Routes {
  def apply(): Route =
    redirectToTrailingSlashIfMissing(StatusCodes.MovedPermanently) {
      concat(
          pathPrefix("api")(ApiRoutes())
      )
    }
}
