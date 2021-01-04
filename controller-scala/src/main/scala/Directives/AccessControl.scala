import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.headers.RawHeader

object AccessControl {
  def apply(): Directive0 = respondWithDefaultHeader(
    RawHeader("Access-Control-Allow-Origin", "http://localhost/")
  )
}
