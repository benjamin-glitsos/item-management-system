import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.StandardRoute

object Complete {
  def text(body: String): StandardRoute = {
    complete(
      HttpEntity(
        ContentTypes.`text/plain(UTF-8)`,
        body
      )
    )
  }

  def json(body: String): StandardRoute = {
    complete(
      HttpEntity(
        ContentTypes.`application/json`,
        body
      )
    )
  }
}
