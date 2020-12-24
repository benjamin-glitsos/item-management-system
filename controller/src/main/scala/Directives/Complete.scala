import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._

object Complete {
  def text(body: String) = {
    complete(
      HttpEntity(
        ContentTypes.`text/plain(UTF-8)`,
        body
      )
    )
  }

  def json(body: String) = {
    complete(
      HttpEntity(
        ContentTypes.`application/json`,
        body
      )
    )
  }
}
