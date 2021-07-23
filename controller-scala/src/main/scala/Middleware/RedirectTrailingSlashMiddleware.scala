import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.MovedPermanently

object RedirectTrailingSlashMiddleware {
  def apply(): Directive0 = redirectToTrailingSlashIfMissing(MovedPermanently)
}
