import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object Routes {
  def apply(): Route = extractRequestEntity { entity =>
    {
      val body = entity.dataBytes

      redirectToTrailingSlashIfMissing(StatusCodes.MovedPermanently) {
        concat(
            pathPrefix("users")(UsersRoutes(body))
        )
      }
    }
  }
}
